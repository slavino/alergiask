package com.hustaty.android.alergia.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {

	public static String getContent(String url) {
		String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try{
            HttpResponse response = client.execute(request);
            result = HttpHelper.request(response);
        }catch(Exception ex){
            result = "Failed!";
        }
        return result;
	}
	
}
