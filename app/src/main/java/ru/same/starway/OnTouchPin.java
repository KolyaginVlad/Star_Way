package ru.same.starway;

public interface OnTouchPin {
    void onTouchPin(int id);

    void onTouchOutsidePin();

    void onTouchText(String text);

    void onLongClick(float x, float y);
}
