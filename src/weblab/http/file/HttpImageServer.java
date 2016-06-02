package weblab.http.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import weblab.http.HttpRequest;

/**
 * Server images back to the client
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpImageServer implements HttpFileServer {
	@Override
	public void serve(HttpRequest request, File file) throws IOException {
		// create a data stream
		DataOutputStream out = new DataOutputStream(request.getOutputStream());

		// create an input stream for the file
		FileInputStream fileInput = new FileInputStream(file);

		// write content length header
		out.writeBytes("Content-Length: " + fileInput.available() + HttpRequest.EOL);
		out.writeBytes("Connection: close" + HttpRequest.EOL);
		out.writeBytes(HttpRequest.EOL);

		// write the file byte wise to the client
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fileInput.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}

		fileInput.close();
	}
}
