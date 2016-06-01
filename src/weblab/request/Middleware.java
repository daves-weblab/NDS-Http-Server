package weblab.request;

public interface Middleware<T extends Request> {
	public boolean execute(T request);
}
