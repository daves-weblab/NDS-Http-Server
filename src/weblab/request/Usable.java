package weblab.request;

public interface Usable {
	public boolean applicable(Request request);
	
	public void use(Request request, Response response, Nextable next);
}
