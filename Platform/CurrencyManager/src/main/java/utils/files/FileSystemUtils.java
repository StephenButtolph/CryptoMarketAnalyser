package utils.files;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Map;

public class FileSystemUtils {
	private static FileSystem fs;
	
	static {
		fs = null;
	}
	
	public static FileSystem getFileSystem(URI uri, Map<String, ?> env) throws IOException {
		if (fs == null) {
			fs = FileSystems.newFileSystem(uri, env);
		}
		return fs;
	}
}
