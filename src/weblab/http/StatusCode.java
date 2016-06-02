package weblab.http;

/**
 * All possible status codes for requests
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public enum StatusCode {
	OK(200, StatusCodeType.SUCCESS), BAD_REQUEST(400, StatusCodeType.CLIENT_ERROR), NOT_FOUND(404,
			StatusCodeType.CLIENT_ERROR);

	/**
	 * the number of the status code
	 */
	private int mStatusCode;

	/**
	 * the type of the status code
	 */
	private StatusCodeType mType;

	private StatusCode(int statusCode, StatusCodeType type) {
		mStatusCode = statusCode;
		mType = type;
	}

	/**
	 * get the numerical representation of the status code
	 * 
	 * @return the numerical repsresentation
	 */
	public int getStatusCode() {
		return mStatusCode;
	}

	/**
	 * get the type of the status code
	 * 
	 * @return the type
	 */
	public StatusCodeType getType() {
		return mType;
	}

	/**
	 * check if the status code is an error code
	 * 
	 * @return true if an error code, false otherwise
	 */
	public boolean isError() {
		switch (getType()) {
		case CLIENT_ERROR:
		case SERVER_ERROR:
			return true;

		default:
			return false;
		}
	}
}
