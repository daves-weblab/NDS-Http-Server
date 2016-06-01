package weblab.http;

public enum Method {
	GET("GET");
	
	private String mSlug;
	
	private Method(String slug) {
		mSlug = slug;
	}
	
	public String getSlug() {
		return mSlug;
	}
	
	public static Method fromSlug(String slug) throws IllegalArgumentException {
		for(Method method : Method.values()) {
			if(method.getSlug().equals(slug)) {
				return method;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
