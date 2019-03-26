package ru.losev.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.math.Rect;

public class Sprite extends Rect {

    private float scale = 1f;
    private float angle;
    private TextureRegion[] regions;
    private int frame;

    public Sprite(TextureRegion... regions) {
        this.regions = regions;
    }

    public void update(float delta){

    }

    public void draw(SpriteBatch batch){
        batch.draw(regions[frame], getLeft(), getBottom(), halfWidth, halfHeight, getWidth(), getHeight(), scale, scale, angle);
    }

    public void resize(Rect worldBounds){

    }

    public boolean touchUp(Vector2 touch, int pointer){
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setHeightProportion(float height){
        float aspect = regions[frame].getRegionWidth() / (float)regions[frame].getRegionHeight();
        setHeight(height);
        setWidth(height * aspect);
    }
}
