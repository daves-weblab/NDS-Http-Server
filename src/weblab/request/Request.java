package weblab.request;

import java.io.InputStream;
import java.io.OutputStream;

import weblab.server.Server;

public abstract class Request {
	private Server mServer;
	
	private InputStream mIn;
	private OutputStream mOut;
	
	private boolean mResolved;
	
	public void setServer(Server server) {
		mServer = server;
	}
	
	public Server getServer() {
		return mServer;
	}
	
	public void setInputStream(InputStream in) {
		mIn = in;
	}

	public void setOutputStream(OutputStream out) {
		mOut = out;
	}

	public InputStream getInputStream() {
		return mIn;
	}

	public OutputStream getOutputStream() {
		return mOut;
	}
	
	public void stopPropagation() {
		mResolved = true;
	}
	
	public boolean isResolved() {
		return mResolved;
	}
	
	public abstract void resolve();
}
