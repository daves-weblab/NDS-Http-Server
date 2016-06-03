package weblab.http.header;

/**
 * Not supported in current version
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ReferrerHeader implements Header {
	public static final String IDENTIFIER = "Referer";
	
	private String mReferer;
	
	public ReferrerHeader(String referer) {
		mReferer = referer;
	}
	
	@Override
	public String toString() {
		return IDENTIFIER + ": " + mReferer + EOL;
	}
}
