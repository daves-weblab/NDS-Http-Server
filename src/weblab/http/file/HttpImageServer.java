package weblab.http.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import weblab.http.HttpRequest;

public class HttpImageServer implements HttpFileServer {
	@Override
	public void serve(HttpRequest request, File file) throws IOException {
		DataOutputStream out = new DataOutputStream(request.getOutputStream());
		FileInputStream fileInput = new FileInputStream(file);
		
		out.writeBytes("Content-Length: " + fileInput.available() + HttpRequest.EOL);
		out.writeBytes("Connection: close" + HttpRequest.EOL);
		out.writeBytes(HttpRequest.EOL);

		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fileInput.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		
		fileInput.close();
	}
}
