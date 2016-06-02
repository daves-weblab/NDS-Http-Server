package weblab.http.file;

import java.io.File;
import java.io.IOException;

import weblab.http.HttpRequest;

/**
 * Serves a file over HTTP
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public interface HttpFileServer {
	/**
	 * serve the file by a request
	 * 
	 * @param request
	 *            the request
	 * @param file
	 *            the file to server
	 *            
	 * @throws IOException
	 */
	public void serve(HttpRequest request, File file) throws IOException;
}
