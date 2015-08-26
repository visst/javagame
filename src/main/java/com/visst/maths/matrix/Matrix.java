package com.visst.maths.matrix;

import static com.visst.utils.BufferUtils.createFloatBuffer;

import java.io.InputStream;
import java.nio.FloatBuffer;
public class Matrix {

	public float[] elements = new float[4 * 4];

	public Matrix() {
	
	}

	public static Matrix identity() {
		Matrix result = new Matrix();
		result.elements[0+0*0] = 1.0f;
		result.elements[1+1*4] = 1.0f;
		result.elements[2+2*4] = 1.0f;
		result.elements[3+3*4] = 1.0f;
		return result;
	}
	
	public FloatBuffer toFloatBuffer() {
		return createFloatBuffer(elements);
	}
	

}
