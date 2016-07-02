package weblab.http.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.typesafe.config.Config;

/**
 * Mime type handling enumeration
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class Mime {
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
	public static String getMimeType(Config conf, File file) throws IOException {
		String mimeString = Files.probeContentType(file.toPath());

		if (mimeString == null) {
			Config config = conf.getConfig("mime.extension");

			String extension = config.getString(getFileExtension(file));

			if (extension != null) {
				return extension;
			}
		}

		return mimeString;
	}

	public static String getFileExtension(File file) {
		String name = file.getName();

		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}
}
