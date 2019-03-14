package ru.losev.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.losev.math.Rect;

public class Sprite extends Rect {

    private float scale = 1f;
    private float angle;
    private TextureRegion[] regions;
    private int frame;

    public Sprite(TextureRegion... regions) {
        this.regions = regions;
    }

    public void draw(SpriteBatch batch){
        batch.draw(regions[frame], getLeft(), getBottom(), halfWidth, halfHeight, getWidth(), getHeight(), scale, scale, angle);
    }
}
