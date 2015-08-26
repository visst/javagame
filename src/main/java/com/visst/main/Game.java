package com.visst.main;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.visst.graphics.Shader;
import com.visst.input.Input;
import com.visst.utils.BufferUtils;

public class Game {

	private GameProperties properties;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long window;
	private Map<String, Shader> shaders = new HashMap<>();

	public Game(GameProperties properties) {
		this.properties = properties;
	}

	public boolean init() {
		if (!setupWindow())
			return false;
		glfwShowWindow(window);

		return true;
	}

	private boolean setupWindow() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		if (glfwInit() != GL11.GL_TRUE) {
			System.err.println("GLFW initialization failed!");
			return false;
		}
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(properties.width, properties.height, properties.title, NULL, NULL);
		if (window == NULL) {
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
		glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
		addShader("Basic", "shaders/basic.frag", "shaders/basic.vert");
		getShader("Basic").enable();
	    float vertices[] = {
	            // Positions         // Colors
	             -1.0f, -1.0f, 0.0f, 
	            1.0f, -1.0f, 0.0f, 
	             0.0f,  0.5f, 0.0f
	        };
	    float colors[] = {
	    		1.0f, 0.0f, 0.0f,
	    		0.0f, 1.0f, 0.0f,
	    		0.0f, 0.0f, 1.0f
	    };
	    int vao = glGenVertexArrays();
	    glBindVertexArray(vao);
	    int vbo = glGenBuffers();
	    glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glEnableVertexAttribArray(0);
		
		int tbo = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, tbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(colors), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		while (glfwWindowShouldClose(window) == GL_FALSE) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glBindVertexArray(vao);
			glDrawArrays(GL_TRIANGLES, 0, 3);
			glBindVertexArray(0);
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	public void dispose() {
		glfwDestroyWindow(window);
		glfwTerminate();
		errorCallback.release();
	}

	private void addShader(String name, String fragmentShaderPath, String vertexShaderPath) {
		shaders.put(name, new Shader(vertexShaderPath, fragmentShaderPath));
	}

	private Shader getShader(String name) {
		return shaders.get(name);
	}

	private void centerOnScreen() {
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - properties.width) / 2,
				(GLFWvidmode.height(vidmode) - properties.height) / 2);
	}
}
