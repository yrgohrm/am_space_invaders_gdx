package se.yrgo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.yrgo.game.AlienGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// this is where we configure how our program starts on the desktop
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 400;
		new LwjglApplication(new AlienGame(), config);
	}
}
