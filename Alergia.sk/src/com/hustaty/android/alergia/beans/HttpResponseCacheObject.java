package com.hustaty.android.alergia.beans;

import java.io.Serializable;
import java.util.Date;

public class HttpResponseCacheObject implements Serializable {

	/**
	 * The seriaVersionUID.
	 */
	private static final long serialVersionUID = -854600323107229970L;
	
	private String responseText;
	private Date cachedTime;
	private Date expirationTime;

	public HttpResponseCacheObject(String responseText, Date cachedTime) {
		this(responseText, cachedTime, null);
	}

	public HttpResponseCacheObject(String responseText, Date cachedTime, Date expirationTime) {
		super();
		this.responseText = responseText;
		this.cachedTime = cachedTime;
		this.expirationTime = expirationTime;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Date getCachedTime() {
		return cachedTime;
	}

	public void setCachedTime(Date cachedTime) {
		this.cachedTime = cachedTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	
}
