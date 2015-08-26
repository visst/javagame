package com.visst.graphics;

import static com.visst.utils.FileUtils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private final int shaderID;
	private String vertexShaderData;
	private String fragmentShaderData;

	public Shader(String vertexShaderPath, String fragmentShaderPath) {
		vertexShaderData = loadFileAsString(vertexShaderPath);
		fragmentShaderData = loadFileAsString(fragmentShaderPath);
		shaderID = createShaderProgram();
	}

	private int createShaderProgram() {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vertexShaderData);
		glShaderSource(fragID, fragmentShaderData);
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}

		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}

		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		glLinkProgram(program);
		glValidateProgram(program);

		glDeleteShader(vertID);
		glDeleteShader(fragID);

		return program;

	}
}
