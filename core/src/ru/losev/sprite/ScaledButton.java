package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;

public abstract class ScaledButton extends Sprite {

    private static final float BUTTON_SCALE = 0.9f;

    private int pointer;
    private boolean isPressed;

    public ScaledButton(TextureRegion regions) {
        super(regions);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isPressed || !isMe(touch)){
            return false;
        }

        isPressed = true;
        this.pointer = pointer;
        setScale(BUTTON_SCALE);

        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(!isPressed || this.pointer != pointer){
            return false;
        }

        setScale(1f);
        isPressed = false;

        if(isMe(touch)){
            action();
        }

        return true;
    }

    protected abstract void action();
}
