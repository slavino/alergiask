package com.hustaty.android.alergia.util;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hustaty.android.alergia.beans.HttpResponseCacheObject;

public class HttpUtil {

	public static String getContent(String url) {
		String result = "";
		
		HttpResponseCacheObject cachedObject = CacheUtil.get(url);
		if(cachedObject != null) {
			return cachedObject.getResponseText();
		}
		
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try{
            HttpResponse response = client.execute(request);
            result = HttpHelper.request(response);
            CacheUtil.getInstance().put(url, new HttpResponseCacheObject(result, new Date()));
        }catch(Exception ex){
            result = "Failed!";
        }
        return result;
	}
	
}
