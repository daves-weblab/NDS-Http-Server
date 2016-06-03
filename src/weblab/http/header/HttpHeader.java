package weblab.http.header;

import weblab.http.statuscode.StatusCode;

/**
 * Header to define the Http Version in use
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpHeader implements Header {
	private String mHttpVersion;
	private StatusCode mStatusCode;

	public HttpHeader(String httpVersion, StatusCode statusCode) {
		mHttpVersion = httpVersion;
		mStatusCode = statusCode;
	}

	@Override
	public String toString() {
		return mHttpVersion + " " + mStatusCode.getStatusCode() + " " + mStatusCode.getMessage() + EOL;
	}
}
