package weblab.request;

public interface Middleware {
	public void execute(Request request, Response response, Nextable next);
}
