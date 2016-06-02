package weblab.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import weblab.request.Request;
import weblab.request.RequestFactory;

/**
 * This class represents a single connection to a client's browser. It creates a
 * Request instance and resolves the request
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class Connection extends Thread implements Session {
	private UUID mSessionId;

	/**
	 * the server this connection is hosted on
	 */
	private Server mServer;

	/**
	 * the socket to the client's browser
	 */
	private Socket mSocket;

	public Connection(UUID sessionId, final Server server, final Socket socket) {
		mSessionId = sessionId;
		mServer = server;
		mSocket = socket;
	}

	@Override
	public void run() {
		final String connectionName = "Connection-" + Thread.currentThread().getId();
		mServer.log(connectionName + ": opening connection ...");

		InputStream in = null;
		OutputStream out = null;

		try {
			// fetch in and out streams from the socket
			in = getSocket().getInputStream();
			out = getSocket().getOutputStream();

			// build a request based on what the client sent to the server
			Request request = RequestFactory.build(in, out);

			// if a request could be built, resolve it
			if (request != null) {
				request.setServer(getServer());
				request.resolve();
			}

			// close the streams
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// close the socket connection
			close();
		} catch (IOException e1) {
			mServer.log("Connection-" + Thread.currentThread().getId() + ": something failed while closing connection");
		}

		mServer.log("Connection-" + Thread.currentThread().getId() + ": closing ...");
	}

	/**
	 * set the server the connection is hosted on
	 */
	@Override
	public Server getServer() {
		return mServer;
	}

	/**
	 * get the id of this connection
	 */
	@Override
	public UUID getSessionId() {
		return mSessionId;
	}
	
	/**
	 * get the socket which is connected to the client's browser
	 * 
	 * @return the socket
	 */
	public Socket getSocket() {
		return mSocket;
	}

	/**
	 * close the socket connection
	 * 
	 * @throws IOException
	 *             if something failed while closing the socket
	 */
	public synchronized void close() throws IOException {
		getServer().clientDisconnected(this);

		if (!mSocket.isClosed()) {
			mSocket.close();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mSessionId == null) ? 0 : mSessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (mSessionId == null) {
			if (other.mSessionId != null)
				return false;
		} else if (!mSessionId.equals(other.mSessionId))
			return false;
		return true;
	}
}
