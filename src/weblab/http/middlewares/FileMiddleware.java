package weblab.http.middlewares;

import java.io.File;
import java.io.IOException;

import weblab.http.file.FileServerJob;
import weblab.http.file.Mime;
import weblab.http.header.ConnectionHeader;
import weblab.http.header.ContentLengthHeader;
import weblab.http.header.ContentTypeHeader;
import weblab.http.request.HttpRequest;
import weblab.http.statuscode.StatusCode;
import weblab.request.Middleware;

/**
 * Middleware which handles serving files
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class FileMiddleware implements Middleware<HttpRequest> {
	@Override
	public boolean execute(HttpRequest request) {
		// retrieve the requested file
		File file = getFile(request);
		FileServerJob server = null;

		// check if the file exists
		if (file.exists()) {
			try {
				// get the type of server needed for the file
				server = getFileServer(request, file);
				// set the status code to OK
				request.setStatusCode(StatusCode.OK);
				
				server.setFile(file);
				
				request.registerServer(server);
				request.addHeader(new ContentTypeHeader(request.getMime()));
				request.addHeader(new ContentLengthHeader(server.getContentLength()));
				request.addHeader(new ConnectionHeader(false));
				
				return true;
			} catch (Exception e) {
				System.err.println("FileMiddleware: could not serve file ...");
				e.printStackTrace();

				// something went wrong, Bad Request
				request.setStatusCode(StatusCode.BAD_REQUEST);
				return false;
			}
		} else {
			// file not found
			request.setStatusCode(StatusCode.NOT_FOUND);
			return false;
		}
	}

	private File getFile(HttpRequest request) {
		File file = new File("./public_html" + request.getQuery().getQuery());
		
		if(file.isDirectory()) {
			file = new File(file.getAbsolutePath() + "/index.html");
		}
		
		return file;
	}

	private FileServerJob getFileServer(HttpRequest request, File file) throws IOException, InstantiationException, IllegalAccessException {
		// get the mime type of the file
		String mimeString = Mime.getMimeType(request.getServer(), file);
		// retrieve the Mime instance
		Mime mimeType = Mime.fromString(request.getServer(), mimeString);

		// set the mime for the request
		request.setMime(mimeString);

		// return the server for the file
		return mimeType.getServer();
	}
}
