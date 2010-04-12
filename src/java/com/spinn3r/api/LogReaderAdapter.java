package com.spinn3r.api;

public interface LogReaderAdapter 
{
	public void onStart(String url);
	
	public void onEnd(String url);
	
	public void onError(String url, String message);
}
