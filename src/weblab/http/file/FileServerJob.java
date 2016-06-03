package weblab.http.file;

import java.io.File;

import weblab.http.request.ServerJob;

/**
 * Job for serving files, extends the ServerJob by prefeching the length of the
 * content
 * 
 * @author David Riedl <david.riedl@daves-weblab.com>
 */
public abstract class FileServerJob implements ServerJob {
	private File mFile;

	/**
	 * set the file to serve
	 * 
	 * @param file
	 *            the fiel to serve
	 */
	public void setFile(File file) {
		mFile = file;
	}

	/**
	 * get the file to serve
	 * 
	 * @return the file to serve
	 */
	public File getFile() {
		return mFile;
	}

	/**
	 * get the length of the content
	 * 
	 * @return the content's length
	 */
	public abstract int getContentLength();
}
