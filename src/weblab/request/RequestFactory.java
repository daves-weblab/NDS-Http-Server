package weblab.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import weblab.http.header.HttpHeader;
import weblab.http.method.Method;
import weblab.http.request.HttpRequest;
import weblab.http.request.Query;

/**
 * Creates request based on the protocol (http, ws, ...)
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class RequestFactory {
	/**
	 * create a new request
	 * 
	 * @param in
	 *            the input stream
	 * @param out
	 *            the output stream
	 * 
	 * @return the built request
	 * 
	 * @throws IOException
	 *             if something failed while creating the request
	 */
	public static Request build(InputStream in, OutputStream out) throws IOException {
		// TODO find out which protocol, currently HTTP only anyways
		Request request = buildHttpRequest(in, out);

		// was it able to build the request?
		if (request == null)
			return null;

		// set the streams
		request.setInputStream(in);
		request.setOutputStream(out);

		return request;
	}

	/**
	 * build a new HttpRequest object
	 * 
	 * @param in
	 *            the input stream
	 * @param out
	 *            the output stream
	 *            
	 * @return a new request object
	 */
	private static HttpRequest buildHttpRequest(InputStream in, OutputStream out) {
		// let's read what the client sent
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String http;

		try {
			// first line gives information about the Http protocol
			http = reader.readLine();

			if (http != null) {
				System.out.println(http);
				// the request looks something like this:
				// GET|POST /theUrl HTTP_VERSION
				
				// let's get the needed information (token == " ")
				StringTokenizer tokenizer = new StringTokenizer(http);
			
				// get the method
				String method = tokenizer.nextToken();
				
				// get the query url (also extract parameters)
				Query query = new Query(tokenizer.nextToken());
				
				String httpVersion = tokenizer.nextToken();

				// TODO maybe include headers later for caching etc.
				List<String> headers = new LinkedList<>();
				String header;

				while ((header = reader.readLine()) != null && !header.equals("")) {
					headers.add(header);
				}

				return new HttpRequest(Method.fromSlug(method), query, httpVersion, headers);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
