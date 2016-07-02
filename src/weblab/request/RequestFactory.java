package weblab.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.typesafe.config.Config;

import weblab.http.method.Method;
import weblab.http.request.Query;

public class RequestFactory {
	public static Request build(InputStream in, Config config) throws IOException {
		// let's read what the client sent
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String http;

		try {
			// first line gives information about the Http protocol
			http = reader.readLine();

			if (http != null) {
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

				Request request = new Request(config, in, Method.fromSlug(method), query, httpVersion, headers);

				return request;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
