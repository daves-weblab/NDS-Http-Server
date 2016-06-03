package weblab.http.header;

import java.util.Arrays;

/**
 * Abstracts headers
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public interface Header {
	/**
	 * EOL for headers and response
	 */
	public static final String EOL = "\r\n";

	public String toString();

	/**
	 * create a header instance from the sent request header string
	 * 
	 * @param header
	 *            textual header sent from client
	 *            
	 * @return the accroding header object
	 */
	public static Header fromString(String header) {
		String[] parts = header.split("\\:", 2);

		if (parts.length != 2)
			return null;

		String identifier = parts[0];
		String value = parts[1].trim();

		switch (identifier) {
		case ConnectionHeader.IDENTIFIER:
			return new ConnectionHeader(value.equals("keep-alive"));

		case AcceptHeader.IDENTIFIER:
			return new AcceptHeader(Arrays.asList(value.split(",")));

		case UserAgentHeader.IDENTIFIER:
			return new UserAgentHeader(value);
		}

		return null;
	}
}
