package weblab.http.file;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weblab.http.HttpRequest;

public class HttpTextHtmlServer implements HttpFileServer {
	@Override
	public void serve(HttpRequest request, File file) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		
		reader.close();
		
		DataOutputStream out = new DataOutputStream(request.getOutputStream());
		
		out.writeBytes("Content-Length: " + content.length() + HttpRequest.EOL);
		out.writeBytes("Connection: close" + HttpRequest.EOL);
		out.writeBytes(HttpRequest.EOL);
		
		out.writeBytes(content.toString());
	}
}
