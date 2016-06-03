package weblab.http.request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import weblab.http.header.Header;
import weblab.http.header.HttpHeader;
import weblab.http.header.ServerHeader;
import weblab.http.method.Method;
import weblab.http.statuscode.StatusCode;
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
	 * the HTTP version in use
	 */
	private String mHttpVersion;
	
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
	private List<Header> mOutHeaders;

	private List<ServerJob> mServers;

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
	public HttpRequest(Method method, Query query, String httpVersion, List<String> headers) {
		mMethod = method;
		mQuery = query;
		mHttpVersion = httpVersion;
		mHeaders = parseHeaders(headers);

		mOutHeaders = new LinkedList<>();
		mServers = new LinkedList<>();
	}

	public void registerServer(ServerJob server) {
		mServers.add(server);
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

		try {
			writeHeaders();

			for (ServerJob server : mServers) {
				try {
					server.serve(getOutputStream());
				} catch (IOException e) {
					System.err.println("error serving content in request");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("could not serve content");
			e.printStackTrace();
		}
	}

	/**
	 * add a header
	 * 
	 * @param header
	 *            the header
	 */
	public void addHeader(Header header) {
		mOutHeaders.add(header);
	}
	
	public List<Header> getHeaders() {
		return mHeaders;
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
	
	public String getMime() {
		return mMime;
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
	
	public StatusCode getStatusCode() {
		return mStatusCode;
	}
	
	public String getHttpVersion() {
		return mHttpVersion;
	}

	private List<Header> parseHeaders(List<String> headers) {
		List<Header> parsedHeaders = new LinkedList<>();
		Header parsedHeader = null;

		for (String header : headers) {
			if ((parsedHeader = Header.fromString(header)) != null) {
				parsedHeaders.add(parsedHeader);
			}
		}

		return parsedHeaders;
	}

	private void writeHeaders() throws IOException {
		DataOutputStream out = new DataOutputStream(getOutputStream());

		System.out.println(mOutHeaders);
		
		out.writeBytes(pickHttpHeader().toString());
		out.writeBytes(pickServerHeader().toString());
		
		for(Header header : mOutHeaders) {
			out.writeBytes(header.toString());
		}

		out.writeBytes(Header.EOL);
	}
	
	private Header pickHttpHeader() {
		return pickHeader(HttpHeader.class);
	}
	
	private Header pickServerHeader() {
		return pickHeader(ServerHeader.class);
	}
	
	private Header pickHeader(Class<? extends Header> header) {
		Iterator<Header> iterator = mOutHeaders.iterator();
		Header current;
		
		while(iterator.hasNext()) {
			current = iterator.next();
			
			if(header.isAssignableFrom(current.getClass())) {
				iterator.remove();
				return current;
			}
		}
		
		return null;
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
