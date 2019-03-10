package ru.losev;

import com.badlogic.gdx.Game;
import ru.losev.screen.MenuScreen;

public class StarGame extends Game {
	
	@Override
	public void create () {
		init();
	}

	private void init() {
		setScreen(new MenuScreen());
	}

}
