package ru.losev.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private SpriteBatch batch;
    private float speed;
    private Texture logo;
    private Vector2 logoPosition;
    private Texture target;
    private Vector2 targetPosition;
    private Vector2 movement;
    private boolean isManual;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;


    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void init() {
        batch = new SpriteBatch();
        logo = new Texture("logo.jpg");
        target = new Texture("target.jpg");
        targetPosition = new Vector2(500, 500);
        logoPosition = new Vector2();
        movement = new Vector2();
        speed = 70f;
        isManual = false;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        move(delta);

        batch.begin();

        batch.draw(target, targetPosition.x, targetPosition.y);
        batch.draw(logo, logoPosition.x, logoPosition.y);

        batch.end();
    }

    private void move(float delta){
        movement.set(0, 0);

        if(!isManual){
            movement = targetPosition.cpy().sub(logoPosition);
        }
        else {
            movement.x = left ? -1 : right ? 1 : 0;
            movement.y = up ? 1 : down ? -1 : 0;
        }

        movement.nor();
        movement.scl(speed * delta);
        logoPosition.add(movement);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        targetPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode){
            case Input.Keys.R:{
                isManual = !isManual;
                break;
            }
            case Input.Keys.LEFT:{
                if(isManual){
                    right = false;
                    left = true;
                }
                break;
            }
            case Input.Keys.RIGHT:{
                if(isManual){
                    left = false;
                    right = true;
                }
                break;
            }
            case Input.Keys.UP:{
                if(isManual){
                    down = false;
                    up = true;
                }
                break;
            }
            case Input.Keys.DOWN:{
                if(isManual){
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

    @Override
    public void dispose() {
        batch.dispose();

        logo.dispose();
        target.dispose();

        super.dispose();
    }
}
