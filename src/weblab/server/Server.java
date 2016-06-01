package weblab.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import weblab.http.HttpRequest;
import weblab.request.Middleware;

public abstract class Server {
	private FileWriter mLog;
	private DateFormat mFormat;
	private boolean _DEBUG_ = true;

	private ServerSocket mServerSocket;
	private ConnectionHandler mConnectionHandler;

	private List<Middleware<HttpRequest>> mHttpMiddlewares;

	/**
	 * custom constructor, needs to be called by subclasses
	 * 
	 * @param port
	 *            the port on which the server will listen
	 * @throws IOException
	 *             when the ServerSocket can not be opened
	 */
	public Server(final int port) throws IOException {
		mServerSocket = new ServerSocket(port);

		mConnectionHandler = new ConnectionHandler(this);
		mFormat = new SimpleDateFormat("HH:mm:ss:SSS");

		mHttpMiddlewares = new LinkedList<>();

		try {
			mLog = new FileWriter(new File("log.txt"));
		} catch (IOException e) {
			_DEBUG_ = false;
			System.err.println("[" + mFormat.format(new Date()) + "] could not open logfile");
		}
	}

	public void attachHttpMiddleware(Middleware<HttpRequest> middleware) {
		mHttpMiddlewares.add(middleware);
	}

	public List<Middleware<HttpRequest>> getHttpMiddlewares() {
		return mHttpMiddlewares;
	}

	/**
	 * starts the server and initializes the ConnectionHandler waiting for new
	 * Clients trying to connect calling {@link #clientConnected(Socket)} on the
	 * server
	 */
	public void start() {
		System.out.println("starting server ...");

		if (running()) {
			mConnectionHandler.start();
		}
	}

	/**
	 * shuts down the server by closing the {@link ServerSocket} and calls
	 * {@link #cleanUp()} on the server
	 */
	public synchronized void shutdown() {
		System.out.println("shutting down server ...");

		if (running()) {
			log("initializing server-shutdown ...");

			try {
				mServerSocket.close();
			} catch (IOException e) {
				log("error while closing the server-socket");
			}

			cleanUp();
		}
	}

	/**
	 * get the {@link ServerSocket} of the server
	 * 
	 * @return the {@link ServerSocket} on which the server is accepting
	 *         connections
	 */
	public ServerSocket getSocket() {
		return mServerSocket;
	}

	/**
	 * check if the server is running or not
	 * 
	 * @return true if the server is running, false otherwise
	 */
	public synchronized boolean running() {
		if (mServerSocket == null) {
			return false;
		}
		return !mServerSocket.isClosed();
	}

	/**
	 * called by the {@link ConnectionHandler} when a client connected
	 * 
	 * @param socket
	 *            which has been opened by ServerSocket
	 */
	public abstract void clientConnected(final Socket socket);

	/**
	 * called after closing the ServerSocket to clean up anyhting necessary
	 */
	public abstract void cleanUp();

	/**
	 * write a given message to the log-file
	 * 
	 * @param message
	 *            the message to be written
	 */
	public synchronized void log(final String message) {
		if (_DEBUG_ && mLog != null) {
			try {
				mLog.append("[" + mFormat.format(new Date()) + "] " + message + "\n\n");
				mLog.flush();
			} catch (IOException e) {
				System.err
						.println("[" + mFormat.format(new Date()) + "] something went wrong while writing to log-file");
			}
		}
	}
}
