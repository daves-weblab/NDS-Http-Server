package weblab.http;

import java.util.HashMap;

public class Query {
	private String mQuery;
	private HashMap<String, String> mParameters;

	public Query() {
		this("", new HashMap<String, String>());
	}

	public Query(String queryString) {
		String[] queryParts = queryString.split("\\?");

		try {
			mQuery = queryParts[0];
			mParameters = Query.buildParameters(queryParts[1]);
		} catch (Exception e) {
			// no query parameters
		}
	}

	public Query(String query, HashMap<String, String> parameters) {
		mQuery = query;
		mParameters = parameters;
	}

	public String getQuery() {
		return mQuery;
	}

	public void setQuery(String query) {
		mQuery = query;
	}

	public HashMap<String, String> getParameters() {
		return mParameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		mParameters = parameters;
	}

	public void addParameter(String key, String value) {
		mParameters.put(key, value);
	}

	public static HashMap<String, String> buildParameters(String parameterString) {
		HashMap<String, String> parameters = new HashMap<>();

		String[] parameterParts = parameterString.split("\\&");
		for (String parameter : parameterParts) {
			try {
				String[] parameterPair = parameter.split("\\=");
				parameters.put(parameterPair[0], parameterPair[1]);
			} catch (Exception e) {
				// ignore invalid parameter Strings
			}
		}

		return parameters;
	}
}
