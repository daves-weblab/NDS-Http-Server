package weblab.request;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.typesafe.config.Config;

import weblab.http.header.Header;
import weblab.http.method.Method;
import weblab.http.request.Query;

public class Request extends HeaderHolder {
	private Config mConfig;
	private InputStream mIn;
	private Method mMethod;
	private Query mQuery;
	private String mHttpVersion;
	private HashMap<String, Object> mArguments;

	public Request(Config config, InputStream in, Method method, Query query, String httpVersion, List<String> headers) {
		mConfig = config;
		mIn = in;
		mArguments = new HashMap<>();

		mMethod = method;
		mQuery = query;
		mHttpVersion = httpVersion;
		parseHeaders(headers);
	}
	
	private void parseHeaders(List<String> headers) {
		Header parsedHeader = null;
		
		for (String header : headers) {
			if ((parsedHeader = Header.fromString(header)) != null) {
				addHeader(parsedHeader);
			}
		}
	}
	
	public Config getConfig() {
		return mConfig;
	}
	
	public InputStream getInputStream() {
		return mIn;
	}
	
	public Method getMethod() {
		return mMethod;
	}
	
	public Query getQuery() {
		return mQuery;
	}
	
	public String getUrl() {
		return mQuery.getQuery();
	}
	
	public HashMap<String, String> getParameters() {
		return mQuery.getParameters();
	}
	
	public String getHttpVersion() {
		return mHttpVersion;
	}

	public Request addArgument(String key, Object value) {
		mArguments.put(key, value);
		return this;
	}

	public Object getArgument(String key) {
		return getArgument(key, Object.class);
	}

	public <T> T getArgument(String key, Class<T> as) {
		Object value = mArguments.get(key);

		if (value == null)
			return null;

		try {
			return as.cast(value);
		} catch (ClassCastException e) {
			return null;
		}
	}
}
