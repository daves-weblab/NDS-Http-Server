package weblab.http.middlewares;

import weblab.http.header.HttpHeader;
import weblab.http.request.HttpRequest;
import weblab.request.Middleware;

public class HttpHeaderMiddleware implements Middleware<HttpRequest> {
	@Override
	public boolean execute(HttpRequest request) {
		request.addHeader(new HttpHeader(request.getHttpVersion(), request.getStatusCode()));
		return true;
	}
}
