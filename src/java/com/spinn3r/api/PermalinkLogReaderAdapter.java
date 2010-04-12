package com.spinn3r.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class PermalinkLogReaderAdapter implements LogReaderAdapter {

	private final Set<String> doneURLS =  new HashSet<String>();
	private final Set<String> incompleteURLS = new HashSet<String>();
	
	
	private void end(String url)
	{
		doneURLS.add(url);
		incompleteURLS.remove(url);
	}
	
	private void start(String url)
	{
		incompleteURLS.add(url);
	}
	
	@Override
	public void onEnd(String url) {
		end(url);
	}

	@Override
	public void onError(String url, String message) {
		end(url);
	}

	@Override
	public void onStart(String url) {
		start(url);
	}
	
	public Set<String> getIncompletURLS()
	{
		return Collections.unmodifiableSet(incompleteURLS);
	}

}
