package weblab.request;

public class MiddlewareUsable implements Usable {
	private Middleware mMiddleware;
	
	public MiddlewareUsable(Middleware middleware) {
		mMiddleware = middleware;
	}

	@Override
	public boolean applicable(Request request) {
		return true;
	}

	@Override
	public void use(Request request, Response response, Nextable next) {
		mMiddleware.execute(request, response, next);
	}
}
