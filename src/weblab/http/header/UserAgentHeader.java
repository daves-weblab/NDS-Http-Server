package weblab.http.header;

/**
 * Not supported in current version
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class UserAgentHeader implements Header {
	public static final String IDENTIFIER = "User-Agent";

	private String mUserAgent;

	public UserAgentHeader(String userAgent) {
		mUserAgent = userAgent;
	}

	@Override
	public String toString() {
		return IDENTIFIER + ": " + mUserAgent + EOL;
	}
}
