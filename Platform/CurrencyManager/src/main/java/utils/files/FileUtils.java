package utils.files;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import constants.Json;
import utils.types.TypeProducer;

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

	public static <T> T loadResource(String filePath, Class<T> classOfT) {
		String file = readResource(filePath);
		return Json.GSON.fromJson(file, classOfT);
	}

	public static <T> T loadResource(String filePath, TypeProducer typeProducer) {
		return loadResource(filePath, typeProducer.getType());
	}

	public static <T> T loadResource(String filePath, Type type) {
		String file = readResource(filePath);
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

	public static String readResource(String filePath) {
		try {
			String toReturn = new String(Files.readAllBytes(getResourcePath(filePath)));
			System.out.println(toReturn);
			return toReturn;
		} catch (InvalidPathException | IOException e) {
			return null;
		}
	}

	public static boolean write(String filePath, String file) {
		try {
			Files.write(Paths.get(filePath), file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
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

	public static boolean appendResource(String filePath, String str) {
		try {
			Files.write(getResourcePath(filePath), str.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static Path getResourcePath(String filePath) {
		URL url = FileUtils.class.getResource(filePath);
		try {
			URI uri = url.toURI();
			Map<String, String> env = new HashMap<>();
			String[] array = uri.toString().split("!");
			FileSystem fs = FileSystemUtils.getFileSystem(URI.create(array[0]), env);
			Path path = fs.getPath(array[1]);
			return path;
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
			try {
				return Paths.get(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				return Paths.get(url.getPath());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Paths.get(url.getPath());
		}
	}
}
