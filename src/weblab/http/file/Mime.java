package weblab.http.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map.Entry;

import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import weblab.server.Server;

/**
 * Mime type handling enumeration
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public enum Mime {
	TEXT_HTML(new HttpTextHtmlServer()), IMAGE(new HttpImageServer());

	private HttpFileServer mServer;

	private Mime(HttpFileServer server) {
		mServer = server;
	}

	public HttpFileServer getServer() {
		return mServer;
	}

	/**
	 * get a mime by a textual represention (see the server.conf)
	 * 
	 * @param server
	 *            the server which holds the configuration
	 * @param mimeString
	 *            textual representation
	 * 
	 * @return the mime type, or null if not supported
	 */
	public static Mime fromString(Server server, String mimeString) {
		ConfigObject config = server.getConfig().getObject("mime.server");

		for (Entry<String, ConfigValue> mimeValues : config.entrySet()) {
			ConfigList mimeStrings = ((ConfigList) mimeValues.getValue());

			for (ConfigValue mimeValue : mimeStrings) {
				if (mimeString.equals(mimeValue.unwrapped().toString())) {
					return Mime.valueOf(mimeValues.getKey());
				}
			}
		}

		return null;
	}

	/**
	 * get the mime type of a file
	 * 
	 * @param file
	 *            the file
	 *            
	 * @return the mime type of the file
	 * 
	 * @throws IOException
	 */
	public static String getMimeType(File file) throws IOException {
		return Files.probeContentType(file.toPath());
	}
}
