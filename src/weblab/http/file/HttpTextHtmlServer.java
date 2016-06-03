package weblab.http.file;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Serves a simple text file to the client. (Css, Js, Html, ...)
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public class HttpTextHtmlServer extends FileServerJob {
	private boolean mRead;
	private String mContent;
	
	private void read() throws IOException {
		if(!mRead) {
			StringBuilder content = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(getFile()));
			
			// read the file line by line
			String line;
			while((line = reader.readLine()) != null) {
				content.append(line);
			}
			
			reader.close();
			
			mContent = content.toString();
			mRead = true;
		}
	}
	
	@Override
	public int getContentLength() {
		try {
			read();
			return mContent.length();
		} catch (IOException e) {
			return 0;
		}
	}
	
	@Override
	public void serve(OutputStream o) throws IOException {
		read();
		
		// create a data stream
		DataOutputStream out = new DataOutputStream(o);
		
		// write the file back to the client
		out.writeBytes(mContent);
	}
}
