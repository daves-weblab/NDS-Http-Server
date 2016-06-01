package weblab.http;

public enum StatusCode {
	OK(200, StatusCodeType.SUCCESS), BAD_REQUEST(400, StatusCodeType.CLIENT_ERROR), NOT_FOUND(404,
			StatusCodeType.CLIENT_ERROR);

	private int mStatusCode;
	private StatusCodeType mType;

	private StatusCode(int statusCode, StatusCodeType type) {
		mStatusCode = statusCode;
		mType = type;
	}

	public int getStatusCode() {
		return mStatusCode;
	}

	public StatusCodeType getType() {
		return mType;
	}

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
