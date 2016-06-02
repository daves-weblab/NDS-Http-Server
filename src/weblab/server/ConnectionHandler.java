package weblab.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * This thread handles incoming connection requests and delegates connected
 * sockets to the server instance the ConnectionHandler is working for.
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class ConnectionHandler extends Thread {
	/**
	 * the server instance this handler is working for
	 */
	private Server mServer;

	/**
	 * create a new ConnectionHandler for a certain server instance
	 * 
	 * @param server
	 *            the server instance this handler will work for
	 */
	public ConnectionHandler(final Server server) {
		mServer = server;
	}

	@Override
	public void run() {
		mServer.log("ConnectionHandler: starting ...");
		Socket socket;

		// while the server is running wait for new connections
		while (!mServer.getSocket().isClosed()) {
			try {
				mServer.log("ConnectionHandler: waiting for a new client ...");

				// accept the socket
				socket = mServer.getSocket().accept();

				mServer.log("ConnectionHandler: client connected ...");

				// delegate the new socket connection to the server instance
				mServer.clientConnected(socket);
			} catch (SocketException e) {
				// nothing to do here as this exception is being exploited to
				// close the ConnectionHandler
			} catch (IOException e) {
				mServer.log("ConnectionHandler: something went terrible wrong [" + e.getMessage() + "]");
			}
		}

		mServer.log("ConnectionHandler: terminating ...");
	}
}
