package ru.losev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture back;
	TextureRegion region;
	
	@Override
	public void create () {
		init();
	}

	private void init(){
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		back = new Texture("background.jpg");
		region = new TextureRegion(back, 200, 200, 100, 100);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.5f, .5f, .5f, .7f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(.3f, .8f, 1f, .7f);
		batch.draw(back, 300, 500);
		batch.draw(img, 0, 0);
		batch.draw(region, 450, 1400);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
