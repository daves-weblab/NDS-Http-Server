# Simple Http Server Express.js Style #

This is a simple implementation of a Http Server in Java. Currently it only serves static files like HTML, Css, Javascript and Images and enables some simple routing mechanisms.

## Execution ##

The server.jar in the dist folder can be used to execute the server. It serves all files from the public_html.

## Routing ##

The server is written similar to express.js. The only things needed to instantiate the server is a config and a base server.


```
#!java

Config config = ConfigFactory.parseFile(...);
Server server = new MultithreadedServer(config.getInt("server.port"), config);

```

After that we are good to go and can start attaching middlewares and routes to the server. Like in express.js the server itself is also a router.


```
#!java

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
        // listens to /
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
// makes the /test route available under /workspace/text
server.use("/workspace", router);

// no route matched till now apparently, so let's return 404
server.use((request, response, next) -> {
	if(response.getStatusCode() == null) {
		response.setStatusCode(StatusCode.NOT_FOUND);
	}
});

```

Ok after setting up the server all that is left, is to start it


```
#!java

server.start();

```

The Configuration File looks like this

```
#!json

"server": {
	"port": 9090,
	"static": "./public_html"
},

"mime": {
	"extension": {
		"js": "application/javascript",
		"css": "text/css",
		"html": "text/html",
		"jpg": "image/jpeg",
		"jpeg": "image/jpeg",
		"png": "image/png",
		"ico": "image/x-icon"
	}
}

```
