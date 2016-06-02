package weblab.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import weblab.request.Middleware;
import weblab.request.Request;

/**
 * Implements a single HttpRequest.
 *
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpRequest extends Request {
	/**
	 * EOL for headers and response
	 */
	public static final String EOL = "\r\n";

	/**
	 * the method of the request
	 */
	private Method mMethod;

	/**
	 * the URL-Query
	 */
	private Query mQuery;

	/**
	 * headers the client sent
	 */
	private List<Header> mHeaders;

	/**
	 * string representation of the request mime type
	 */
	private String mMime;

	/**
	 * status code of the request
	 */
	private StatusCode mStatusCode;

	/**
	 * headers that will be sent back
	 */
	private List<String> mOutHeaders;

	/**
	 * create a new http request
	 * 
	 * @param method
	 *            the method
	 * @param query
	 *            the url-query
	 * @param headers
	 *            sent headers
	 */
	public HttpRequest(Method method, Query query, List<String> headers) {
		mMethod = method;
		mQuery = query;
		mHeaders = parseHeaders(headers);
		mOutHeaders = new LinkedList<>();
	}

	/**
	 * resolve the request by using the registered middlewares
	 */
	@Override
	public void resolve() {
		// in the current version only get requests are supported
		if (getMethod() == Method.GET) {
			// execute all middlewares
			for (Middleware<HttpRequest> middleware : getServer().getHttpMiddlewares()) {
				// check if the request was resolved already
				if (!this.isResolved()) {
					middleware.execute(this);
				}
			}
		} else {
			// everything else is not supported yet
			setStatusCode(StatusCode.BAD_REQUEST);
		}
	}

	/**
	 * send back the status code and the content type defined by the internal
	 * stored mime type.
	 * 
	 * @throws IOException
	 */
	public void writeStatus() throws IOException {
		// server header
		String server = "Server: Java NDS HttpServer" + EOL;

		// http header
		String status = "HTTP/1.1 ";

		// content type header
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

		// send back the headers
		DataOutputStream out = new DataOutputStream(getOutputStream());

		out.writeBytes(status);
		out.writeBytes(server);

		if (mMime != null) {
			out.writeBytes(contentType);
		}
	}

	/**
	 * add a header
	 * 
	 * @param header
	 *            the header
	 */
	public void addHeader(String header) {
		mOutHeaders.add(header);
	}

	/**
	 * set the mime type
	 * 
	 * @param mime
	 *            the mime type
	 */
	public void setMime(String mime) {
		mMime = mime;
	}

	/**
	 * set the status code
	 * 
	 * @param statusCode
	 *            the status code
	 */
	public void setStatusCode(StatusCode statusCode) {
		mStatusCode = statusCode;
	}

	private List<Header> parseHeaders(List<String> headers) {
		return new LinkedList<Header>();
	}

	/**
	 * get the method of the request
	 * 
	 * @return the method
	 */
	public Method getMethod() {
		return mMethod;
	}

	/**
	 * get the query of the request
	 * 
	 * @return the query
	 */
	public Query getQuery() {
		return mQuery;
	}

	@Override
	public String toString() {
		return mMethod.getSlug() + ", " + mQuery;
	}
}
