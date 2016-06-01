package weblab.http.middlewares;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import weblab.http.HttpRequest;
import weblab.http.StatusCode;
import weblab.http.file.HttpFileServer;
import weblab.http.file.Mime;
import weblab.request.Middleware;

public class FileMiddleware implements Middleware<HttpRequest> {
	@Override
	public boolean execute(HttpRequest request) {
		File file = getFile(request);
		HttpFileServer server = null;
		
		if (file.exists()) {
			try {
				server = getFileServer(request, file);
				request.setStatusCode(StatusCode.OK);
				
				request.stopPropagation();
			} catch (Exception e) {
				System.err.println("FileMiddleware: could not serve file ...");
				e.printStackTrace();
				
				request.setStatusCode(StatusCode.BAD_REQUEST);
			}
		} else {
			request.setStatusCode(StatusCode.NOT_FOUND);
		}
		
		try {
			request.writeStatus();
			
			if(server == null) {
				return false;
			}
			
			server.serve(request, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private File getFile(HttpRequest request) {
		return new File("./public_html" + request.getQuery());
	}

	private HttpFileServer getFileServer(HttpRequest request, File file) throws IOException {
		String mime = Files.probeContentType(file.toPath());
		Mime mimeType = Mime.fromString(mime);
		
		request.setMime(mime);
		
		return mimeType.getServer();
	}
}
