package weblab.http.header;

/**
 * Header to define the length of the content being sent
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ContentLengthHeader implements Header {
	public static final String IDENTIFIER = "Content-Length";

	private int mContentLength;

	public ContentLengthHeader(int contentLength) {
		mContentLength = contentLength;
	}

	@Override
	public String toString() {
		return IDENTIFIER + ": " + mContentLength + EOL;
	}
}
