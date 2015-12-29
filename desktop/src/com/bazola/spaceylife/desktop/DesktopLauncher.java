package com.bazola.spaceylife.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bazola.spaceylife.LibGDXGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;


public class DesktopLauncher {
	public static void main (String[] arg) {
		
		/*
        Settings settings = new Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        settings.pot = true;
        //TexturePacker.process(settings, "texturePacker/angry01_jpg", "texturePacker", "angry01");
        //TexturePacker.process(settings, "texturePacker/big_alien", "texturePacker", "bigAlien01");
        //TexturePacker.process(settings, "texturePacker/hello01_jpg", "texturePacker", "hello01");
        //TexturePacker.process(settings, "texturePacker/idle01_jpg", "texturePacker", "idle01");
        //TexturePacker.process(settings, "texturePacker/idle02_jpg", "texturePacker", "idle02");
        //TexturePacker.process(settings, "texturePacker/screen", "texturePacker", "screen01");
        */
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LibGDXGame(), config);
	}
}
