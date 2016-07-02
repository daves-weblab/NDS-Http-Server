package weblab.request;

public class RouteUsable implements Usable {
	private String mUrl;
	private Route mRoute;
	private String mPrefix;
	
	public RouteUsable(String url, Route route) {
		mUrl = url;
		mRoute = route;
		mPrefix = "";
	}
	
	public void setPrefix(String prefix) {
		mPrefix = prefix;
	}
	
	@Override
	public boolean applicable(Request request) {
		return request.getUrl().equals(mPrefix + mUrl);
	}

	@Override
	public void use(Request request, Response response, Nextable next) {
		mRoute.execute(request, response);
	}

}
