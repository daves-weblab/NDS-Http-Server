package weblab.http.header;

/**
 * Header to define the type of the content being sent
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ContentTypeHeader implements Header {
	public static final String IDENTIFIER = "Content-Type";

	private String mContentType;

	public ContentTypeHeader(String contentType) {
		mContentType = contentType;
	}

	@Override
	public String toString() {
		return IDENTIFIER + ": " + mContentType + EOL;
	}
}
