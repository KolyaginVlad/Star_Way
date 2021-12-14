package ru.same.starway;

import java.util.Objects;

public class MapText {
    private float x;
    private float y;
    private String text;

    public MapText(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapText mapText = (MapText) o;
        return Double.compare(mapText.x, x) == 0 && Double
                .compare(mapText.y, y) == 0 && text.equals(mapText.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, text);
    }
}
