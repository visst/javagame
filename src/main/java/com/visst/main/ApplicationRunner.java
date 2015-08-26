package com.visst.main;

import org.lwjgl.Sys;

public class ApplicationRunner {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private static final String WINDOW_TITLE = "Visst Java Game";

	public static void main(String... args) {
		System.out.println(Sys.getVersion());
		GameProperties props = new GameProperties(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
		Game gameInstance = new Game(props);
		
		if (!gameInstance.init()) {
			System.err.println("Application will exit.");
			return;
		}
		gameInstance.loop();
		gameInstance.dispose();
	}

}
