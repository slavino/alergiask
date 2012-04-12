package com.hustaty.android.alergia.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class HttpHelper {

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			if(ex != null) {
				LogUtil.appendLog("#HttpHelper.request(): " + ex.getMessage());
			} else {
				LogUtil.appendLog("#HttpHelper.request(): Problem occured.");
			}
			result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml/>";
		}
		return result;
	}
	
}
