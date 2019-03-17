package ru.losev.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.losev.math.MatrixUtils;
import ru.losev.math.Rect;
import ru.losev.sprite.Background;
import ru.losev.sprite.Star;

public abstract class BaseScreen extends InputAdapter implements Screen {

    private static final int STAR_COUNT = 50;

    protected SpriteBatch batch;

    private boolean isInit = false;

    private Rect screenBounds; // границы области рисования в px
    private Rect worldBounds; // границы проекции в мировых координатах
    private Rect glBounds; // квадрат OpenGL

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    private Background background;
    private Texture backgroundTexture;

    private ArrayList<Star> stars;
    private TextureAtlas atlas;
    private Game game;

    public BaseScreen(Game game) {
        this.game = game;
    }

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

        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new ArrayList<Star>(STAR_COUNT);
        for(int i = 0; i < STAR_COUNT; i++ ){
            stars.add(new Star(atlas));
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public final void render(float delta) {
        update(delta);
        draw(delta);
    }

    private void draw(float delta){
        Gdx.gl.glClearColor(.5f, .5f, .5f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        renderInternal(delta);
        batch.end();
    }

    protected void renderInternal(float delta){

    }

    private void update(float delta){
        for (Star star: stars) {
            star.update(delta);
        }

        updateInternal(delta);
    }

    protected void updateInternal(float delta){

    }

    @Override
    public final void resize(int width, int height) {
        screenBounds.setSize(width, height).setLeft(0).setBottom(0);

        float aspect = width / (float)height;
        worldBounds.setSize(1f * aspect, 1f);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        resize(worldBounds);
    }

    private void resize(Rect worldBounds){
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }

        resizeInternal(worldBounds);
    }

    protected void resizeInternal(Rect worldBounds){

    }

    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        return touchDown(touch, pointer);
    }

    public boolean touchDown(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        return touchUp(touch, pointer);
    }

    public boolean touchUp(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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

    public Game getGame() {
        return game;
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        atlas.dispose();
        batch.dispose();
    }

}
