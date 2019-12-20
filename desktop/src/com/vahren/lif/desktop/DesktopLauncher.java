package com.vahren.lif.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vahren.lif.Lif;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Lif lif = new Lif();
		config.width = (int)(lif.getA() * lif.getW());
		config.height = (int)(lif.getA() * lif.getH());
		new LwjglApplication(lif, config);
	}
}
