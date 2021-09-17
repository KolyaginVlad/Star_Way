package ru.same.starway;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

class MySubsamplingScaleImageView extends SubsamplingScaleImageView {


    private OnDrawListener onDrawListener;
    private OnTouchPin onTouchPin;
    private boolean focused = true;


    ArrayList<MapPin> mapPins;
    ArrayList<DrawPin> drawnPins;
    Context context;
    private Activity mActivity;

    public MySubsamplingScaleImageView(Activity activity) {
        this(activity, null);
        this.context = activity;
        mActivity = activity;
    }

    public MySubsamplingScaleImageView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initialise();
    }

    public void setPins(ArrayList<MapPin> mapPins) {
        this.mapPins = mapPins;
        initialise();
        invalidate();
    }

    private void initialise() {

    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (focused) {
            focused = true;
            return super.onTouchEvent(event);
        } else {
            return true;
        }
    }


    @Override
    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public void setOnDrawListener(OnDrawListener listener) {
        onDrawListener = listener;
    }

    public void setOnTouchPinListener(OnTouchPin onTouchPinListener) {
        onTouchPin = onTouchPinListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during       setup.
        if (!isReady()) {
            return;
        }

        drawnPins = new ArrayList<>();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#4B3AFF"));
        Typeface medium = ResourcesCompat.getFont(context, R.font.medium);
        paint.setTypeface(medium);
        paint.setAntiAlias(true);
        float density = getResources().getDisplayMetrics().densityDpi;


        for (int i = 0; i < mapPins.size(); i++) {
            MapPin mPin = mapPins.get(i);
            //Bitmap bmpPin = Utils.getBitmapFromAsset(context, mPin.getPinImgSrc());
            String spec =
                    mActivity.getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                            .getString(Constants.spectrum.name(), Spectrum.visible.name());
            int id = 0;
            if (spec.equals(Spectrum.visible.name()))
                id = R.drawable.visible_no_back;
            else if (spec.equals(Spectrum.radio.name()))
                id = R.drawable.radio_no_back;
            else if (spec.equals(Spectrum.infrared.name()))
                id = R.drawable.infrared_no_back;
            else if (spec.equals(Spectrum.ultraviolet.name()))
                id = R.drawable.ultraviolet_no_back;
            else if (spec.equals(Spectrum.xray.name()))
                id = R.drawable.xray_no_back;
            Bitmap bmpPin =
                    BitmapFactory.decodeResource(this.getResources(), id);

            float w = bmpPin.getWidth() * getScale() * 7;
            float h = bmpPin.getHeight() * getScale() * 7;
            bmpPin = Bitmap.createScaledBitmap(bmpPin, (int) w, (int) h, true);
            paint.setTextSize(90 * getScale());

            PointF vPin = sourceToViewCoord(mPin.getPoint());
            //in my case value of point are at center point of pin image, so we need to adjust it here

            float vX = vPin.x - (bmpPin.getWidth() / 2);
            float vY = vPin.y - bmpPin.getHeight();


            canvas.drawBitmap(bmpPin, vX, vY, paint);
            canvas.drawText("крабовидная", vX + (bmpPin.getWidth() / 2),
                    vY + (bmpPin.getHeight() / 2), paint);
            canvas.drawText("туманность", vX + (bmpPin.getWidth() / 2),
                    vY + (bmpPin.getHeight() / 2) + 60 * getScale(), paint);
            //add added pin to an Array list to get touched pin
            DrawPin dPin = new DrawPin();
            dPin.setStartX(mPin.getX() - w / 2);
            dPin.setEndX(mPin.getX() + w / 2);
            dPin.setStartY(mPin.getY() - h / 2);
            dPin.setEndY(mPin.getY() + h / 2);
            dPin.setId(mPin.getId());
            drawnPins.add(dPin);
        }
        setTouchListener();
        onDrawListener.onDraw();
    }

    private void setTouchListener() {
        final GestureDetector gestureDetector =
                new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (isReady() && drawnPins != null) {
                            PointF tappedCoordinate = new PointF(e.getX(), e.getY());
                            float density = getResources().getDisplayMetrics().densityDpi;
                            Bitmap bmpPin =
                                    BitmapFactory.decodeResource(getResources(),
                                            R.drawable.visible_no_back);

                            float w = bmpPin.getWidth() * getScale() * 7;
                            float h = bmpPin.getHeight() * getScale() * 7;
                            bmpPin = Bitmap.createScaledBitmap(bmpPin, (int) w, (int) h, true);
                            int blockWidth = bmpPin.getWidth();
                            int blockHeight = bmpPin.getHeight();

                            // check if deepLinkBitmap is touched
                            for (MapPin deeplink : mapPins) {
                                PointF deeplinkCoordinate =
                                        sourceToViewCoord(new PointF(deeplink.getX(),
                                                deeplink.getY()));
                                int deeplinkX = (int) (deeplinkCoordinate.x - (bmpPin
                                        .getWidth() / 2));
                                int deeplinkY =
                                        (int) (deeplinkCoordinate.y - bmpPin.getHeight());

                                // center coordinate -/+ blockWidth actually sets touchable area to 2x icon size
                                if (tappedCoordinate.x >= deeplinkX && tappedCoordinate.x <= deeplinkX + blockWidth &&
                                        tappedCoordinate.y >= deeplinkY && tappedCoordinate.y <= deeplinkY + blockHeight) {
//                                    Log.d(tag, deeplink.id+" was taped");
                                    if (deeplink.id == 1) {
                                        animateScaleAndCenter(0.5f, new PointF(4050f, 4250f))
                                                .start();
                                    }
                                    onTouchPin.onTouchPin(deeplink.id);
                                    focused = false;
                                    return true;
                                }
                                onTouchPin.onTouchOutsidePin();
                                focused = true;
                            }
                        }
                        return true;
                    }
                });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }
}