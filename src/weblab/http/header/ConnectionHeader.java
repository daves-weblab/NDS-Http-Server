package weblab.http.header;

/**
 * Header to define if a connection should be closed or not
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ConnectionHeader implements Header {
	public static final String IDENTIFIER = "Connection";

	private boolean mKeepAlive;

	public ConnectionHeader(boolean keepAlive) {
		mKeepAlive = keepAlive;
	}

	@Override
	public String toString() {
		return IDENTIFIER + ": " + (mKeepAlive ? "keep-alive" : "close") + EOL;
	}
}
