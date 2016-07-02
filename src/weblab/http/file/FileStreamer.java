package weblab.http.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Job for serving files, extends the ServerJob by prefeching the length of the
 * content
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class FileStreamer {
	private File mFile;

	/**
	 * set the file to serve
	 * 
	 * @param file
	 *            the fiel to serve
	 */
	public void setFile(File file) {
		mFile = file;
	}

	/**
	 * get the file to serve
	 * 
	 * @return the file to serve
	 */
	public File getFile() {
		return mFile;
	}

	/**
	 * get the length of the content
	 * 
	 * @return the content's length
	 */
	public int getContentLength() {
		try {
			FileInputStream input = new FileInputStream(getFile());

			int available = input.available();
			input.close();

			return available;
		} catch (IOException e) {
			return 0;
		}
	}
	
	public void stream(DataOutputStream out) throws IOException {
		// create an input stream for the file
		FileInputStream fileInput = new FileInputStream(getFile());

		// write the file byte wise to the client
		byte[] buffer = new byte[1024];
		int bytesRead = 0;

		while ((bytesRead = fileInput.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}

		fileInput.close();
	}
}
