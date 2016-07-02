package weblab.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import weblab.request.Middleware;
import weblab.request.MiddlewareUsable;
import weblab.request.Nextable;
import weblab.request.Request;
import weblab.request.Response;
import weblab.request.Route;
import weblab.request.RouteUsable;
import weblab.request.Usable;

public class Router implements Nextable, Usable {
	private List<Usable> mUsables;
	
	private Iterator<Usable> mNext;
	private Request mRequest;
	private Response mResponse;
	
	public Router() {
		mUsables = new LinkedList<>();
	}

	public void setPrefix(String prefix) {
		for(Usable usable : mUsables) {
			if(usable instanceof RouteUsable) {
				((RouteUsable) usable).setPrefix(prefix);
			}
		}
	}
	
	public Router use(Middleware middleware) {
		mUsables.add(new MiddlewareUsable(middleware));
		
		return this;
	}
	
	public Router use(String url, Route route) {
		mUsables.add(new RouteUsable(url, route));
		
		return this;
	}
	
	public Router use(String prefix, Router router) {
		router.setPrefix(prefix);
		mUsables.add(router);
		
		return this;
	}
	
	public void execute(Request request, Response response) {
		mNext = mUsables.iterator();
		mRequest = request;
		mResponse = response;
		
		apply();
	}

	@Override
	public void apply() {
		if(mNext.hasNext()) {
			Usable usable = mNext.next();
			
			if(usable.applicable(mRequest)) {
				usable.use(mRequest, mResponse, this);
			} else {
				apply();
			}
		}
	}

	@Override
	public boolean applicable(Request request) {
		return true;
	}

	@Override
	public void use(Request request, Response response, Nextable next) {
		execute(request, response);
		
		next.apply();
	}
}
