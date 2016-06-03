package weblab.http.request;

import java.util.HashMap;

/**
 * Represents a Query for a request, query string and parameters
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class Query {
	/**
	 * the query string
	 */
	private String mQuery;

	/**
	 * the passed parameters
	 */
	private HashMap<String, String> mParameters;

	/**
	 * create an empty query
	 */
	public Query() {
		this("", new HashMap<String, String>());
	}

	/**
	 * create a query from a query string and extract the parameters
	 * 
	 * @param queryString
	 *            the query string
	 */
	public Query(String queryString) {
		String[] queryParts = queryString.split("\\?");

		try {
			mQuery = queryParts[0];
			mParameters = Query.buildParameters(queryParts[1]);
		} catch (Exception e) {
			// no query parameters
		}
	}

	/**
	 * create a query by passing the query string and the parameters
	 * 
	 * @param query
	 * @param parameters
	 */
	public Query(String query, HashMap<String, String> parameters) {
		mQuery = query;
		mParameters = parameters;
	}

	/**
	 * get the query string
	 * 
	 * @return the query string
	 */
	public String getQuery() {
		return mQuery;
	}

	/**
	 * set the query string
	 * 
	 * @param query
	 *            the query string
	 */
	public void setQuery(String query) {
		mQuery = query;
	}

	/**
	 * get the parameters
	 * 
	 * @return the parameters
	 */
	public HashMap<String, String> getParameters() {
		return mParameters;
	}

	/**
	 * set the parameters
	 * 
	 * @param parameters
	 *            the parameters
	 */
	public void setParameters(HashMap<String, String> parameters) {
		mParameters = parameters;
	}

	/**
	 * add a paramter to the query
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addParameter(String key, String value) {
		mParameters.put(key, value);
	}

	/**
	 * build parameters from a query string
	 * 
	 * @param parameterString
	 *            the query string
	 *            
	 * @return the extracted parameters
	 */
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
