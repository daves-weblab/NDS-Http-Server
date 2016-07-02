package main;

import java.io.File;
import java.io.IOException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import weblab.http.header.ContentTypeHeader;
import weblab.http.header.ServerHeader;
import weblab.http.middlewares.FileMiddleware;
import weblab.http.statuscode.StatusCode;
import weblab.server.MultithreadedServer;
import weblab.server.Router;
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
			Config config = ConfigFactory.parseFile(new File("./config/server.conf"));
			
			// create a new server instance on port defined in the config
			server = new MultithreadedServer(config.getInt("server.port"), config);

			// express.js style routing, pretty neat right?
			server
			// this middleware attaches the Server: NDS Http Server header to each request, just for fun
			.use((request, response, next) -> {
				response.addHeader(new ServerHeader("NDS Http Server"));
				next.apply();
			})
			
			// serve static files
			.use(new FileMiddleware(config.getString("server.static")))
		
			// no file found? let's try these routes then
			.use("/", (request, response) -> {
				// let's return only hello world
				response.setStatusCode(StatusCode.OK);
				response.addHeader(new ContentTypeHeader("text/html"));
				response.setContent("Hello World");
			});
			
			/*
			 * ok that is neat, but what about nested routers?
			 * oh thats possible too? cool! let's try that.
			 */
			
			Router router = new Router();
			
			router.use("/test", (request, response) -> {
				response.setStatusCode(StatusCode.OK);
				response.addHeader(new ContentTypeHeader("text/html"));
				response.setContent("I am the nested route /test");
			});
			
			// let's add the sub router
			server.use("/workspace", router);
			
			// no route matched till now apparently, so let's return 404
			server.use((request, response, next) -> {
				if(response.getStatusCode() == null) {
					response.setStatusCode(StatusCode.NOT_FOUND);
				}
			});
			
			// start the server, it's serving time
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
