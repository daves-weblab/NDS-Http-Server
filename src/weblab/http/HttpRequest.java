package weblab.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import weblab.request.Middleware;
import weblab.request.Request;

public class HttpRequest extends Request {
	public static final String EOL = "\r\n";
	private Method mMethod;
	private Query mQuery;
	private List<Header> mHeaders;

	private String mMime;
	private StatusCode mStatusCode;
	private List<String> mOutHeaders;

	public HttpRequest(Method method, Query query, List<String> headers) {
		mMethod = method;
		mQuery = query;
		mHeaders = parseHeaders(headers);
		mOutHeaders = new LinkedList<>();
	}

	@Override
	public void resolve() {
		if (getMethod() == Method.GET) {
			for (Middleware<HttpRequest> middleware : getServer().getHttpMiddlewares()) {
				if (!this.isResolved()) {
					middleware.execute(this);
				}
			}
		} else {
			// everything else is not supported yet
			setStatusCode(StatusCode.BAD_REQUEST);
		}
	}

	public void writeStatus() throws IOException {
		String server = "Server: Java NDS HttpServer" + EOL;
		String status = "HTTP/1.1 ";
		String contentType = "Content-Type: " + mMime + EOL;

		switch (mStatusCode) {
		case OK:
			status += "200 OK";
			break;

		case BAD_REQUEST:
			status += "400 Bad Request";
			break;

		case NOT_FOUND:
			status += "404 Not Found";
		}

		status += " " + EOL;

		DataOutputStream out = new DataOutputStream(getOutputStream());

		out.writeBytes(status);
		out.writeBytes(server);

		if (mMime != null) {
			out.writeBytes(contentType);
		}
	}

	public void addHeader(String header) {
		mOutHeaders.add(header);
	}

	public void setMime(String mime) {
		mMime = mime;
	}

	public void setStatusCode(StatusCode statusCode) {
		mStatusCode = statusCode;
	}

	private List<Header> parseHeaders(List<String> headers) {
		return new LinkedList<Header>();
	}

	public Method getMethod() {
		return mMethod;
	}

	public Query getQuery() {
		return mQuery;
	}

	@Override
	public String toString() {
		return mMethod.getSlug() + ", " + mQuery;
	}
}
