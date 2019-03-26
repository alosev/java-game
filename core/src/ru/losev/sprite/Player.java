package ru.losev.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.losev.StarGame;
import ru.losev.base.Sprite;
import ru.losev.math.Rect;

public class Player extends Sprite {

    private static float V_LEN = 0.01f;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private float speed;
    private Vector2 v;
    private Vector2 target;

    private Rect worldBounds;

    public Player(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(195, 287)[0]);

        setHeightProportion(0.1f);
        speed = 0.2f;
        v = new Vector2();
        target = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(this.worldBounds.getBottom());
        target.set(position);
    }

    @Override
    public void update(float delta) {
        if(StarGame.isAndroid){
            moveToTarget(delta);
        }
        else{
            moveManual(delta);
        }

        checkAndHandleBounds();
    }

    private void moveManual(float delta) {
        v.x = left ? -1 : right ? 1 : 0;
        v.y = up ? 1 : down ? -1 : 0;
        v.nor();

        position.mulAdd(v, speed * delta);
    }

    private void moveToTarget(float delta) {
        v.set(target);

        if(v.sub(position).len() <= V_LEN){
            position.set(target);
        }
        else{
            v.nor();
            position.mulAdd(v, speed * delta);
        }
    }

    private void checkAndHandleBounds(){

        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }

        if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }

        if(getTop() > worldBounds.getTop()){
            setTop(worldBounds.getTop());
        }

        if(getBottom() < worldBounds.getBottom()){
            setBottom(worldBounds.getBottom());
        }

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(StarGame.isAndroid){
            target.set(touch);

            return true;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:{
                if(!StarGame.isAndroid){
                    right = false;
                    left = true;
                }
                break;
            }
            case Input.Keys.RIGHT:{
                if(!StarGame.isAndroid){
                    left = false;
                    right = true;
                }
                break;
            }
            case Input.Keys.UP:{
                if(!StarGame.isAndroid){
                    down = false;
                    up = true;
                }
                break;
            }
            case Input.Keys.DOWN:{
                if(!StarGame.isAndroid){
                    up = false;
                    down = true;
                }
                break;
            }
            default:
                return false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:{
                left = false;
                break;
            }
            case Input.Keys.RIGHT:{
                right = false;
                break;
            }
            case Input.Keys.UP:{
                up = false;
                break;
            }
            case Input.Keys.DOWN:{
                down = false;
                break;
            }
            default:
                return false;
        }
        return true;
    }
}
