package com.hustaty.android.alergia.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hustaty.android.alergia.beans.HttpResponseCacheObject;

public class CacheUtil {

	private static Map<String, HttpResponseCacheObject> cacheMap;
	
	private static CacheUtil instance;
	
	private CacheUtil() {
		CacheUtil.cacheMap = new HashMap<String, HttpResponseCacheObject>();
	}
	
	public static CacheUtil getInstance() {
		if(instance == null) {
			instance = new CacheUtil();
		}
		return instance;
	}
	
	public static HttpResponseCacheObject get(String id) {
		HttpResponseCacheObject cachedObject = getInstance().getCacheMap().get(id);
		if(cachedObject != null) {
			if(cachedObject.getExpirationTime() != null && cachedObject.getExpirationTime().after(new Date())) {
				return cachedObject;
			} else if (cachedObject.getExpirationTime() == null) {
				return cachedObject;
			}
		}
		return null;
	}
	
	public static void put(String id, HttpResponseCacheObject value) {
		if(value.getExpirationTime() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 5);
			value.setExpirationTime(cal.getTime());
		}
		getInstance().getCacheMap().put(id, value);
	}
	

	private Map<String, HttpResponseCacheObject> getCacheMap() {
		getInstance();
		return CacheUtil.cacheMap;
	}
	
}
