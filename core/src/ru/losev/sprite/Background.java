package ru.losev.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.losev.base.Sprite;

public class Background extends Sprite {

    @Override
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.5f, .5f, .5f, .7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
