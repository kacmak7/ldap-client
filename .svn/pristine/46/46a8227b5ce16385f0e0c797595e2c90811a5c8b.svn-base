package org.parafia.util;

import java.io.File;

import org.apache.log4j.Logger;

public class JSONBuilder {
	private static final Logger log = Logger.getLogger(JSONBuilder.class);
	
	public static String buildJson(String filePath, String contextPath) {
		File[] files = FileUtil.getDirectoryList(filePath, false);
		StringBuffer json = new StringBuffer();
		if (files.length > 0) {
			json.append("{");
			json.append("\"children\": [");
			for (File f : files) {
				String absolutePath = f.getAbsolutePath();
				absolutePath = absolutePath.replace("\\", "/");
				json.append("{\"label\": \""+f.getName()+"\",");
				json.append("\"name\": \""+absolutePath+"\",");
				json.append("\"sourceType\": \"json/url\",");
				json.append("\"source\": \""+contextPath+"/mail/getJSON?filePath="+absolutePath+"\"},");
				
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]}");
		}
		
		return json.toString();
	}
	
	
	public static String[] buildFirstJson(String filePath, String contextPath) {
		File[] files = FileUtil.getDirectoryList(filePath, false);

		String[] jsons = new String[files.length];
		if (files.length > 0) {
			int i = 0;
			for (File f : files) {
				StringBuffer json = new StringBuffer();
				String absolutePath = f.getAbsolutePath();
				absolutePath = absolutePath.replace("\\", "/");
				json.append("{\"label\": \""+f.getName()+"\",");
				json.append("\"name\": \""+absolutePath+"\",");
				json.append("\"sourceType\": \"json/url\",");
				json.append("\"source\": \""+contextPath+"/mail/getJSON?filePath="+absolutePath+"\"}");
				jsons[i++] = json.toString();
			}
		}
		
		if (log.isDebugEnabled())
			for (String j : jsons) {
				log.debug(j);
			}
		
		return jsons;
	}
}
