package ru.losev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.losev.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		modifyConfig(config);
		new LwjglApplication(new StarGame(false), config);
	}

	private static void modifyConfig(LwjglApplicationConfiguration config) {
		float aspect = 3f / 4f;
		config.width = 400;
		config.height = (int) (config.width / aspect);
		config.resizable = false;
	}
}
