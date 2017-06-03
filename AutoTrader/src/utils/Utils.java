package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static String readFile(String filePath) throws IOException {
		File file = new File(filePath);
		return readFile(file);
	}
	
	public static String readFile(File file) throws IOException {
		FileReader reader = new FileReader(file);
		BufferedReader in = new BufferedReader(reader);
		return read(in);
	}
	
	public static String read(BufferedReader in) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		String line;
		while((line = in.readLine()) != null){
			builder.append(line);
			builder.append('\n');
		}
		
		String toReturn = builder.toString();
		toReturn = toReturn.trim();
		return toReturn;
	}
}
