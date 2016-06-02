package main;

import java.io.IOException;

import weblab.http.middlewares.FileMiddleware;
import weblab.server.MultithreadedServer;
import weblab.server.Server;

/**
 * executes the multithreaded server
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class Main {
	public static void main(String[] args) {
		Server server;

		try {
			// create a new server instance on port 9090
			server = new MultithreadedServer(9090);

			// time to attach some middlewares
			// this could come from a config for easier usage
			// for now the server is only able to serve files so the
			// FileMiddleware is enough
			server.attachHttpMiddleware(new FileMiddleware());

			// start the server, it's serving time
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
