package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;

public class BadLogic extends Sprite {

    private static float V_LEN = 1f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 buf;

    public BadLogic(TextureRegion region) {
        super(region);

        setSize(5f, 5f);

        touch = new Vector2();
        v = new Vector2();
        buf = new Vector2();
    }

    public void update(){
        buf.set(touch);
        if(buf.sub(position).len() <= V_LEN){
            position.set(touch);
        }
        else{
            position.add(v);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch.set(touch);
        v.set(this.touch).sub(position).setLength(V_LEN);
        return true;
    }

}
