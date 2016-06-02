package weblab.http;

/**
 * Supported Methods
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public enum Method {
	// currently only GET is supported
	GET("GET");

	private String mSlug;

	private Method(String slug) {
		mSlug = slug;
	}

	public String getSlug() {
		return mSlug;
	}

	/**
	 * create a Method by the method's slug
	 * 
	 * @param slug
	 *            slug of the method
	 * @return the actual Method instance
	 * 
	 * @throws IllegalArgumentException
	 */
	public static Method fromSlug(String slug) throws IllegalArgumentException {
		for (Method method : Method.values()) {
			if (method.getSlug().equals(slug)) {
				return method;
			}
		}

		throw new IllegalArgumentException();
	}
}
