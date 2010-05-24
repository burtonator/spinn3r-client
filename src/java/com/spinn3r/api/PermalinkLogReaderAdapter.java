package com.spinn3r.api;


class PermalinkLogReaderAdapter implements LogReaderAdapter {

	private String lastURL;
	private long lastCtr = Long.MIN_VALUE;
	
	
	@Override
	public void onEnd(long ctr, String url) {
		if(lastCtr < ctr) {
		    lastCtr = ctr;
		    lastURL = url;
		}
	}

	
	public String getLastUrl()
	{
		return lastURL;
	}
	
	public long getLastCtr()
	{
	    return lastCtr;
	}

}
