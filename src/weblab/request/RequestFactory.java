package weblab.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import weblab.http.HttpRequest;
import weblab.http.Method;

public class RequestFactory {
	public static Request build(InputStream in, OutputStream out) throws IOException {
		// TODO find out which protocol, currently HTTP only anyways
		Request request = buildHttpRequest(in, out);

		if (request == null)
			return null;

		request.setInputStream(in);
		request.setOutputStream(out);

		return request;
	}

	private static HttpRequest buildHttpRequest(InputStream in, OutputStream out) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String http;
		
		try {
			http = reader.readLine();

			if (http != null) {
				StringTokenizer tokenizer = new StringTokenizer(http);

				String method = tokenizer.nextToken();
				String query = tokenizer.nextToken();

				// TODO use parameters
				query = stripQueryParameters(query);
				
				List<String> headers = new LinkedList<>();
				String header;

				while ((header = reader.readLine()) != null && !header.equals("")) {
					headers.add(header);
				}

				return new HttpRequest(Method.fromSlug(method), query, headers);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private static String stripQueryParameters(String query) {
		return query.split("\\?")[0];
	}
}
