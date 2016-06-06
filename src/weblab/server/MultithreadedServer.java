package weblab.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.typesafe.config.Config;

/**
 * Multithreaded server implementation for resolving requests.
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class MultithreadedServer extends Server {
	private List<Connection> mConnections;

	/**
	 * Create a new instance of MultithreadedServer
	 * 
	 * @param port
	 *            the port the server will listen to
	 * 
	 * @throws IOException
	 *             if the port is already in use
	 */
	public MultithreadedServer(int port, Config config) throws IOException {
		super(port, config);

		if (running()) {
			mConnections = new ArrayList<Connection>();
		}
	}

	/**
	 * called if the ConnectionHandler opened a new connection
	 * 
	 * @param socket
	 *            the socket of the new connection
	 */
	@Override
	public void clientConnected(Socket socket) {
		// create a new connection
		Connection connection = new Connection(UUID.randomUUID(), this, socket);

		// add the connection so it can be terminated if the server is shut down
		mConnections.add(connection);

		// resolve the connection
		connection.start();
	}

	@Override
	public synchronized void clientDisconnected(Connection connection) {
		mConnections.remove(connection);
	}

	/**
	 * close all open connections
	 */
	@Override
	public void cleanUp() {
		for (Connection connection : mConnections) {
			try {
				connection.close();
			} catch (IOException e) {
				// nothing to do here
			}
		}

		mConnections.clear();
	}
}