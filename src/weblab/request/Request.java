package weblab.request;

import java.io.InputStream;
import java.io.OutputStream;

import weblab.server.Server;

/**
 * Abstraction of requests the client sends to the server
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public abstract class Request {
	/**
	 * the server the request is being resolved on
	 */
	private Server mServer;

	/**
	 * input stream of the request
	 */
	private InputStream mIn;

	/**
	 * output stream of the request
	 */
	private OutputStream mOut;

	/**
	 * defines if the request was resolved by a middleware already
	 */
	private boolean mResolved;

	/**
	 * set the server the request is being handled by
	 * 
	 * @param server
	 */
	public void setServer(Server server) {
		mServer = server;
	}

	/**
	 * get the server the request is being handly by
	 * 
	 * @return the server
	 */
	public Server getServer() {
		return mServer;
	}

	/**
	 * set the input stream of the request
	 * 
	 * @param in
	 *            the input stream
	 */
	public void setInputStream(InputStream in) {
		mIn = in;
	}

	/**
	 * set the output stream of the request
	 * 
	 * @param out
	 *            the output stream
	 */
	public void setOutputStream(OutputStream out) {
		mOut = out;
	}

	/**
	 * get the input stream of the request
	 * 
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		return mIn;
	}

	/**
	 * get the output stream of the request
	 * 
	 * @return the output stream
	 */
	public OutputStream getOutputStream() {
		return mOut;
	}

	/**
	 * stop propagation to middlewares, the request is completely resolved
	 * already
	 */
	public void stopPropagation() {
		mResolved = true;
	}

	/**
	 * check if the request is completely resolved already
	 * 
	 * @return true if already resolved, false otherwise
	 */
	public boolean isResolved() {
		return mResolved;
	}

	/**
	 * resolve the request
	 */
	public abstract void resolve();
}
