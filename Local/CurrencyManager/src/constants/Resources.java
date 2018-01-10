package constants;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import exceptions.StaticLoadException;

/**
 * This is a resource manager for gaining access to resource files.
 * 
 * @author Stephen Buttolph
 */
public class Resources {
	private static final String POLONIEX_AUTH_LOCAL_PATH;

	/**
	 * The path to the poloniex API token within the project.
	 */
	public static final String POLONIEX_AUTH_PATH;

	static {
		POLONIEX_AUTH_LOCAL_PATH = "/Poloniex/Auth.auth";

		URL resource = Resources.class.getResource(POLONIEX_AUTH_LOCAL_PATH);
		try {
			POLONIEX_AUTH_PATH = Paths.get(resource.toURI()).toFile().getAbsolutePath();
		} catch (NullPointerException | URISyntaxException e) {
			throw new StaticLoadException("Poloniex authorization file was not found.");
		}
	}
}
