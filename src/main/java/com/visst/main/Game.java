package com.visst.main;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.visst.input.Input;

import java.nio.ByteBuffer;
 
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
public class Game {

	private GameProperties properties;
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private long window;
	public Game(GameProperties properties) {
		this.properties = properties;
	}

	public boolean init() {
		if(!setupWindow())
			return false;
        glfwShowWindow(window);
 
		return true;
	}

	private boolean setupWindow() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		if(glfwInit() != GL11.GL_TRUE){
			System.err.println("GLFW initialization failed!");
			return false;
		}
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(properties.width,properties.height,properties.title,NULL,NULL);
		if(window == NULL){
			System.err.println("Failed to create a window!");
			return false;
		}
		glfwSetKeyCallback(window, new Input());
        centerOnScreen();
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        return true;
	}


	public void loop() {
	GLContext.createFromCurrent();
		glClearColor(0.2f,0.2f,0.2f,0.0f);
		while(glfwWindowShouldClose(window) == GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	public void dispose() {
		glfwDestroyWindow(window);
		glfwTerminate();
		errorCallback.release();
	}

	private void centerOnScreen() {
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - properties.width) / 2,
				(GLFWvidmode.height(vidmode) - properties.height) / 2);
	}
}
