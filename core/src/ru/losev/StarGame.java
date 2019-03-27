package ru.losev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.losev.screen.GameScreen;
import ru.losev.screen.MenuScreen;

public class StarGame extends Game {

	public static boolean isAndroid;
	private Music music;

	public StarGame(boolean isAndroid) {
		StarGame.isAndroid = isAndroid;
	}

	@Override
	public void create () {
		init();
	}

	private void init() {
		setScreen(new MenuScreen(this));

		music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
	}

	@Override
	public void dispose() {
		music.dispose();
		super.dispose();
	}
}
