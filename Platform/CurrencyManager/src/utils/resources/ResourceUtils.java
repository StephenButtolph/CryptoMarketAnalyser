package utils.resources;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceUtils {
	public static String getResourcePath(String localPath) {
		URL resource = ResourceUtils.class.getResource(localPath);
		try {
			return Paths.get(resource.toURI()).toFile().getAbsolutePath();
		} catch (NullPointerException | URISyntaxException e) {
			return null;
		}
	}
}
