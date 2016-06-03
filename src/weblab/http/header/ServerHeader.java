package weblab.http.header;

/**
 * Header to define the server name
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ServerHeader implements Header {
	private String mServerName;

	public ServerHeader(String serverName) {
		mServerName = serverName;
	}

	@Override
	public String toString() {
		return "Server: " + mServerName + EOL;
	}
}
