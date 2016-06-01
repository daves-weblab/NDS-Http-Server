package weblab.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import weblab.request.Request;
import weblab.request.RequestFactory;

public class Connection extends Thread implements Session {
	private Server mServer;
	private Socket mSocket;

	public Connection(final Server server, final Socket socket) {
		mServer = server;
		mSocket = socket;
	}

	@Override
	public void run() {
		final String connectionName = "Connection-" + Thread.currentThread().getId();
		mServer.log(connectionName + ": opening connection ...");

		// TODO answer the HTTP request
		InputStream in = null;
		OutputStream out = null;

		try {
			in = getSocket().getInputStream();
			out = getSocket().getOutputStream();

			Request request = RequestFactory.build(in, out);

			if (request != null) {
				request.setServer(getServer());
				request.resolve();
			}

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

	@Override
	public Server getServer() {
		return mServer;
	}

	public Socket getSocket() {
		return mSocket;
	}

	public synchronized void close() throws IOException {
		if (!mSocket.isClosed()) {
			mSocket.close();
		}
	}
}
