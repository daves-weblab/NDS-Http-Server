package weblab.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultithreadedServer extends Server {
	private List<Connection> mConnections;
	
	public MultithreadedServer(int port) throws IOException {
		super(port);
		
		if(running()) {
			mConnections = new ArrayList<Connection>();
		}
	}

	@Override
	public void clientConnected(Socket socket) {
		Connection connection = new Connection(this, socket);
		
		mConnections.add(connection);
		connection.start();
	}

	@Override
	public void cleanUp() {
		for(Connection connection : mConnections) {
			try {
				connection.close();
			} catch (IOException e) {
				// nothing to do here
			}
		}
	}
}