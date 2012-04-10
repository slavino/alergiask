package com.hustaty.android.alergia.util;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.StrictMode;
import android.util.Log;

import com.hustaty.android.alergia.beans.HttpResponseCacheObject;

public class HttpUtil {

	public static String getContent(String url) {
		String result = "";
		
		CacheUtil.getInstance();
		HttpResponseCacheObject cachedObject = CacheUtil.get(url);
		if(cachedObject != null) {
			return cachedObject.getResponseText();
		}
		
        HttpClient client = new DefaultHttpClient();
        
        HttpHost httpHost = new HttpHost("www.alergia.sk");
        
        client.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        String utfurl = url;
		
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpGet request = new HttpGet(utfurl.replace("http://www.alergia.sk", ""));
        request.setHeader("host", "www.alergia.sk");
        
        try{
            HttpResponse response = client.execute(httpHost, request);
            result = HttpHelper.request(response);
            CacheUtil.put(url, new HttpResponseCacheObject(result, new Date()));
        }catch(Exception ex){
        	Log.e(LOG_TAG, ex.getMessage());
            result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml/>";
        }
        return result;
	}
	
}
