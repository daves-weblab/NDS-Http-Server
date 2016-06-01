package weblab.http.file;

public enum Mime {
	TEXT_HTML(new HttpTextHtmlServer()), 
	IMAGE(new HttpImageServer());

	private HttpFileServer mServer;

	private Mime(HttpFileServer server) {
		mServer = server;
	}

	public HttpFileServer getServer() {
		return mServer;
	}
	
	public static Mime fromString(String mime) {
		switch (mime) {
		case "text/html":
			return Mime.TEXT_HTML;

		case "image/png":
		case "image/jpeg":
		case "image/x-icon":
			return Mime.IMAGE;
		}

		return null;
	}
}
