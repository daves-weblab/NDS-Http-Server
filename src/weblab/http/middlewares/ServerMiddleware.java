package weblab.http.middlewares;

import weblab.http.header.ServerHeader;
import weblab.http.request.HttpRequest;
import weblab.request.Middleware;

public class ServerMiddleware implements Middleware<HttpRequest> {
	private String mServerName;
	
	public ServerMiddleware(String serverName) {
		mServerName = serverName;
	}
	
	@Override
	public boolean execute(HttpRequest request) {
		request.addHeader(new ServerHeader(mServerName));
		return true;
	}
}
