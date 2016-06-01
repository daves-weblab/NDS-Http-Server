package main;

import java.io.IOException;

import weblab.http.middlewares.FileMiddleware;
import weblab.server.MultithreadedServer;
import weblab.server.Server;

public class Main {
	public static void main(String[] args) {
		Server server;
		
		try {
			server = new MultithreadedServer(9090);
			
			// time to attach some middlewares
			// this could be from a config for easier usage
			server.attachHttpMiddleware(new FileMiddleware());
			
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
