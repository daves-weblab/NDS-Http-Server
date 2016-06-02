package weblab.http.file;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weblab.http.HttpRequest;

/**
 * Serves a simple text file to the client. (Css, Js, Html, ...)
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpTextHtmlServer implements HttpFileServer {
	@Override
	public void serve(HttpRequest request, File file) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// read the file line by line
		String line;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		
		reader.close();
		
		// create a data stream
		DataOutputStream out = new DataOutputStream(request.getOutputStream());
		
		// write back the content length header
		out.writeBytes("Content-Length: " + content.length() + HttpRequest.EOL);
		out.writeBytes("Connection: close" + HttpRequest.EOL);
		out.writeBytes(HttpRequest.EOL);
		
		// write the file back to the client
		out.writeBytes(content.toString());
	}
}
