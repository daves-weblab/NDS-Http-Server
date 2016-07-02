package weblab.request;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import weblab.http.header.Header;

public class HeaderHolder {
	private List<Header> mHeaders = new LinkedList<>();
	
	public List<Header> getHeaders() {
		return mHeaders;
	}

	public void addHeader(Header header) {
		Iterator<Header> iterator = mHeaders.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().getClass().equals(header.getClass())) {
				iterator.remove();
				break;
			}
		}
		
		mHeaders.add(header);
	}
	
	public Header getHeader(Class<? extends Header> header) {
		Iterator<Header> iterator = mHeaders.iterator();
		Header current;
		
		while(iterator.hasNext()) {
			current = iterator.next();
			
			if(header.isAssignableFrom(current.getClass())) {
				iterator.remove();
				return current;
			}
		}
		
		return null;
	}
}
