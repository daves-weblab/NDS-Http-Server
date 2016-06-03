package weblab.http.request;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Job which actually serves back content, all headers have already been written
 * beforehand.
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public interface ServerJob {
	/**
	 * serve the content on the out stream
	 * 
	 * @param out
	 *            the OutputStream to serve content to
	 */
	public void serve(OutputStream o) throws IOException;
}
