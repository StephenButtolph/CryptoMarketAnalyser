package utils.files;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import constants.Json;
import types.TypeProducer;

public class FileUtils {
	public static <T> T load(String filePath, Class<T> classOfT) {
		String file = read(filePath);
		return Json.GSON.fromJson(file, classOfT);
	}

	public static <T> T load(String filePath, TypeProducer typeProducer) {
		return load(filePath, typeProducer.getType());
	}

	public static <T> T load(String filePath, Type type) {
		String file = read(filePath);
		return Json.GSON.fromJson(file, type);
	}

	public static boolean save(String filePath, TypeProducer toSave) {
		return save(filePath, toSave, toSave.getType());
	}

	public static boolean save(String filePath, Object toSave, TypeProducer typeProducer) {
		return save(filePath, toSave, typeProducer.getType());
	}

	public static boolean save(String filePath, Object toSave, Type type) {
		String file = Json.GSON.toJson(toSave, type);
		return write(filePath, file);
	}

	public static String read(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (InvalidPathException | IOException e) {
			return null;
		}
	}

	public static boolean write(String filePath, String file) {
		try {
			Files.write(Paths.get(filePath), file.getBytes());
			return true;
		} catch (InvalidPathException | IOException e) {
			return false;
		}
	}

	public static boolean appendln(String filePath, String line) {
		return append(filePath, line + "\n");
	}

	public static boolean append(String filePath, String str) {
		try {
			Files.write(Paths.get(filePath), str.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
