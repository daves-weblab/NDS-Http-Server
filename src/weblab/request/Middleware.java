package weblab.request;

/**
 * Middlewares that can be registered on a server to handle incoming requests
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 *
 * @param <T>
 *            defines the type of request the middleware is able to handle
 */
public interface Middleware<T extends Request> {
	/**
	 * execute the middleware for a given request
	 * 
	 * @param request
	 *            the request the middleware will resolve
	 * @return true if everything was ok, false otherwise
	 */
	public boolean execute(T request);
}
