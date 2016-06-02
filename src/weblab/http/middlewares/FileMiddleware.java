package weblab.http.middlewares;

import java.io.File;
import java.io.IOException;

import weblab.http.HttpRequest;
import weblab.http.StatusCode;
import weblab.http.file.HttpFileServer;
import weblab.http.file.Mime;
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
		HttpFileServer server = null;

		// check if the file exists
		if (file.exists()) {
			try {
				// get the type of server needed for the file
				server = getFileServer(request, file);
				// set the status code to OK
				request.setStatusCode(StatusCode.OK);

				// stop propagation to other middlewares
				request.stopPropagation();
			} catch (Exception e) {
				System.err.println("FileMiddleware: could not serve file ...");
				e.printStackTrace();

				// something went wrong, Bad Request
				request.setStatusCode(StatusCode.BAD_REQUEST);
			}
		} else {
			// file not found
			request.setStatusCode(StatusCode.NOT_FOUND);
		}

		try {
			request.writeStatus();

			if (server == null) {
				return false;
			}

			server.serve(request, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private File getFile(HttpRequest request) {
		return new File("./public_html" + request.getQuery().getQuery());
	}

	private HttpFileServer getFileServer(HttpRequest request, File file) throws IOException {
		// get the mime type of the file
		String mimeString = Mime.getMimeType(file);
		// retrieve the Mime instance
		Mime mimeType = Mime.fromString(request.getServer(), mimeString);

		// set the mime for the request
		request.setMime(mimeString);

		// return the server for the file
		return mimeType.getServer();
	}
}
