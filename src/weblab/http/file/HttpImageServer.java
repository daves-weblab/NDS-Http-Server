package weblab.http.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Server images back to the client
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpImageServer extends FileServerJob {
	@Override
	public int getContentLength() {
		try {
			FileInputStream fileInput = new FileInputStream(getFile());
			int length = fileInput.available();
			
			fileInput.close();
			
			return length;
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public void serve(OutputStream o) throws IOException {
		// create a data stream
		DataOutputStream out = new DataOutputStream(o);

		// create an input stream for the file
		FileInputStream fileInput = new FileInputStream(getFile());

		// write the file byte wise to the client
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fileInput.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}

		fileInput.close();
	}
}
