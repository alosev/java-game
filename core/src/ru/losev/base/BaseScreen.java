package ru.losev.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.losev.math.MatrixUtils;
import ru.losev.math.Rect;
import ru.losev.sprite.Background;

public abstract class BaseScreen extends InputAdapter implements Screen {

    protected SpriteBatch batch;

    private boolean isInit = false;

    private Rect screenBounds; // границы области рисования в px
    private Rect worldBounds; // границы проекции в мировых координатах
    private Rect glBounds; // квадрат OpenGL

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    private Background background;

    @Override
    public void show() {
        if(!isInit){
            isInit = true;
            init();
        }
    }

    protected void init(){
        batch = new SpriteBatch();

        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);

        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();

        touch = new Vector2();

        background = new Background();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        background.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height).setLeft(0).setBottom(0);

        float aspect = width / (float)height;
        worldBounds.setSize(100f * aspect, 100f);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
    }

    public void resize(Rect worldBounds){

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return true;
    }

    public boolean touchDown(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
