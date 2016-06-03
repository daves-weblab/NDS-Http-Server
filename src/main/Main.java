package main;

import java.io.IOException;

import weblab.http.middlewares.FileMiddleware;
import weblab.http.middlewares.HttpHeaderMiddleware;
import weblab.http.middlewares.ServerMiddleware;
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
			// FileMiddleware is enough, later caching middlewares could be
			// added easily using such a system

			// sets the server header
			server.attachHttpMiddleware(new ServerMiddleware("NDS Simple Http Server"));
			server.attachHttpMiddleware(new FileMiddleware());

			// sets the http header after status code has been set
			server.attachHttpMiddleware(new HttpHeaderMiddleware());

			// start the server, it's serving time
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
