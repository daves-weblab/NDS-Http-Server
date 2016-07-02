package weblab.http.middlewares;

import java.io.File;

import weblab.http.file.FileStreamer;
import weblab.http.file.Mime;
import weblab.http.header.ConnectionHeader;
import weblab.http.header.ContentLengthHeader;
import weblab.http.header.ContentTypeHeader;
import weblab.http.statuscode.StatusCode;
import weblab.request.Middleware;
import weblab.request.Nextable;
import weblab.request.Request;
import weblab.request.Response;

public class FileMiddleware implements Middleware {
	private String mStaticDirectory;
	
	public FileMiddleware(String staticDirectory) {
		mStaticDirectory = staticDirectory;
	}
	
	@Override
	public void execute(Request request, Response response, Nextable next) {
		// retrieve the requested file
		File file = getFile(request);

		// check if the file exists
		if (file.exists() && file.isFile()) {
			try {
				// get the mime type of the file
				String mimeString = Mime.getMimeType(request.getConfig(), file);

				// create a new file server job
				FileStreamer fileStreamer = new FileStreamer();
				fileStreamer.setFile(file);

				// set the status code to OK
				response.setStatusCode(StatusCode.OK);

				response.addHeader(new ContentTypeHeader(mimeString));
				response.addHeader(new ContentLengthHeader(fileStreamer.getContentLength()));
				response.addHeader(new ConnectionHeader(false));
				response.setFileStreamer(fileStreamer);
			} catch (Exception e) {
				System.err.println("FileMiddleware: could not serve file ...");
				e.printStackTrace();

				// something went wrong, Bad Request
				response.setStatusCode(StatusCode.BAD_REQUEST);
			}
		} else {
			// file not found
			response.setStatusCode(StatusCode.NOT_FOUND);
			next.apply();
		}
	}

	private File getFile(Request request) {
		File file = new File(mStaticDirectory + request.getUrl());

		return file;
	}
}
