package com.visst.main;


public class ApplicationRunner {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	public static void main(String... args){
	GameProperties props = new GameProperties(WINDOW_WIDTH, WINDOW_HEIGHT);
	Game gameInstance = new Game(props);
	}

}
