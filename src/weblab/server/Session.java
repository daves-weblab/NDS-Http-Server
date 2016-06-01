package weblab.server;

public interface Session {
	/**
	 * get the server on which the session is running
	 * 
	 * @return the actual server
	 */
	public Server getServer();
}
