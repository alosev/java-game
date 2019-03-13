package ru.losev.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public abstract class BaseScreen extends InputAdapter implements Screen {
    private boolean isInit = false;

    @Override
    public void show() {
        if(!isInit){
            isInit = true;
            init();
        }

        Gdx.input.setInputProcessor(this);
    }

    protected abstract void init();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .5f, .5f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

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

    }

}
