package weblab.http.header;

import java.util.List;

/**
 * Not supported in current version
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class AcceptHeader implements Header {
	public static final String IDENTIFIER = "Accept";
	
	private List<String> mAccept;
	
	public AcceptHeader(List<String> accept) {
		mAccept = accept;
	}
	
	@Override
	public String toString() {
		return IDENTIFIER + ": " + mAccept + EOL;
	}
}
