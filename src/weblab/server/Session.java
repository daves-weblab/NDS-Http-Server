package weblab.server;

import java.util.UUID;

/**
 * Interface connections and sessions implement.
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public interface Session {
	/**
	 * get the server on which the session is running
	 * 
	 * @return the actual server
	 */
	public Server getServer();

	/**
	 * get the session's id
	 * 
	 * @return the session id
	 */
	public UUID getSessionId();
}
