package ru.losev.math;

import com.badlogic.gdx.math.Vector2;

public class Rect {
    public final Vector2 position = new Vector2(); // позиция по центру
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect() {

    }

    public Rect(Rect from) {
        this(from.position.x, from.position.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        position.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return position.x - halfWidth;
    }

    public float getTop() {
        return position.y + halfHeight;
    }

    public float getRight() {
        return position.x + halfWidth;
    }

    public float getBottom() {
        return position.y - halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public Rect set(Rect from) {
        position.set(from.position);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;

        return this;
    }

    public Rect setLeft(float left) {
        position.x = left + halfWidth;
        return this;
    }

    public Rect setTop(float top) {
        position.y = top - halfHeight;
        return this;
    }

    public Rect setRight(float right) {
        position.x = right - halfWidth;
        return this;
    }

    public Rect setBottom(float bottom) {
        position.y = bottom + halfHeight;
        return this;
    }

    public Rect setWidth(float width) {
        this.halfWidth = width / 2f;
        return this;
    }

    public Rect setHeight(float height) {
        this.halfHeight = height / 2f;
        return this;
    }

    public Rect setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
        return this;
    }

    public boolean isMe(Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() && touch.y >= getBottom() && touch.y <= getTop();
    }

    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: position" + position + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}
