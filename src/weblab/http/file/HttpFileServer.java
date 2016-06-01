package weblab.http.file;

import java.io.File;
import java.io.IOException;

import weblab.http.HttpRequest;

public interface HttpFileServer {
	public void serve(HttpRequest request, File file) throws IOException;
}
