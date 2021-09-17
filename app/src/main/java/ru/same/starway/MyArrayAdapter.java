package ru.same.starway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyArrayAdapter extends BaseAdapter {
    private final Context context;
    private final String[] spec;

    private final LayoutInflater layoutInflater;

    public MyArrayAdapter(Context context, String[] spec) {
        this.context = context;
        this.spec = spec;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return spec.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.spinner_item, parent, false);
        } else {
            view = convertView;
        }
        ((TextView) view.findViewById(R.id.textSpinner)).setText(spec[position]);
        return view;

    }
}
