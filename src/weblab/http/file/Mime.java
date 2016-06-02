package weblab.http.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map.Entry;

import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import weblab.server.Server;

public enum Mime {
	TEXT_HTML(new HttpTextHtmlServer()), 
	IMAGE(new HttpImageServer());

	private HttpFileServer mServer;

	private Mime(HttpFileServer server) {
		mServer = server;
	}

	public HttpFileServer getServer() {
		return mServer;
	}
	
	public static Mime fromString(Server server, String mimeString) {
		ConfigObject config = server.getConfig().getObject("mime.server");
		
		for(Entry<String, ConfigValue> mimeValues: config.entrySet()) {
			ConfigList mimeStrings = ((ConfigList) mimeValues.getValue());
			
			for(ConfigValue mimeValue : mimeStrings) {
				if(mimeString.equals(mimeValue.unwrapped().toString())) {
					return Mime.valueOf(mimeValues.getKey());
				}
			}
		}
		
		return null;
	}
	
	public static String getMimeType(File file) throws IOException {
		return Files.probeContentType(file.toPath());
	}
}
