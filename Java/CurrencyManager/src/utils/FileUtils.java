package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

import types.TypeProducer;

public class FileUtils {
	private static final Gson GSON;

	static {
		GSON = new Gson();
	}

	public static <T> T load(String filePath, Class<T> classOfT) {
		String file = read(filePath);
		return GSON.fromJson(file, classOfT);
	}

	public static boolean save(String filePath, TypeProducer toSave) {
		String file = GSON.toJson(toSave, toSave.getType());
		return write(filePath, file);
	}

	public static String read(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean write(String filePath, String file) {
		try {
			Files.write(Paths.get(filePath), file.getBytes());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
