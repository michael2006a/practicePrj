package com.trendmicro.skyaid.common;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class URLMatch {

	
	public static final String regex = "\\b(http|https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;{}]*[-a-zA-Z0-9+&@#/%=~_|{}]";
	
	public static String match(String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		
		StringBuffer sbr = new StringBuffer();
		while (matcher.find()) {
			String tmp = matcher.group();
			if(tmp.contains("trendmicro.com") || tmp.contains(".trend.")) {
				matcher.appendReplacement(sbr, "TMURL");
			} else {
				matcher.appendReplacement(sbr, "NORMALURL");
			}
		}
		
		matcher.appendTail(sbr);
		
		return sbr.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String str = FileUtils.readFileToString(new File("tmp.txt"));
		System.out.println(match(str));
	}
}
