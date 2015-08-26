package com.visst.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	private FileUtils() {
	}

	public static String loadFileAsString(String filePath) {
		StringBuilder result = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer + '\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();

	}
}
