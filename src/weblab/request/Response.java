package weblab.request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import weblab.http.file.FileStreamer;
import weblab.http.header.Header;
import weblab.http.header.HttpHeader;
import weblab.http.statuscode.StatusCode;

public class Response extends HeaderHolder {
	private OutputStream mOut;
	private StatusCode mStatusCode;
	private String mContent;
	private FileStreamer mFileStreamer;

	public Response(OutputStream out) {
		mOut = out;
	}

	public OutputStream getOutputStream() {
		return mOut;
	}

	public void setStatusCode(StatusCode statusCode) {
		mStatusCode = statusCode;
	}

	public StatusCode getStatusCode() {
		return mStatusCode;
	}

	public void setFileStreamer(FileStreamer fileStreamer) {
		mFileStreamer = fileStreamer;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public void sendBack(Request request) throws IOException {
		// create a data stream
		DataOutputStream out = new DataOutputStream(getOutputStream());

		out.writeBytes(new HttpHeader(request.getHttpVersion(), getStatusCode()).toString());

		// send the headers back to the client
		for (Header header : getHeaders()) {
			out.writeBytes(header.toString());
		}

		// empty line as delimiter of headers and content
		out.writeBytes(Header.EOL);

		// write the content back to the client
		if (mContent != null) {
			out.writeBytes(mContent);
		} else if(mFileStreamer != null){
			mFileStreamer.stream(out);
		}
	}
}
