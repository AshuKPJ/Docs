package com.bny.analytics.mlengine;

import java.util.ResourceBundle;

import com.bny.analytics.mlengine.utils.Constants;

public abstract class Executor {

	
	private String hostname = "localhost";
	private int port = 00;
	private long start_timestamp;
	private long end_timestamp;
	
	
	// Constructor
	public Executor(int port) {
		this.port = port;
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		hostname=rb.getString(Constants.HOSTNAME);
		start_timestamp=System.currentTimeMillis();
	}
	
	
	protected abstract void kill();

	protected abstract void startSession();
	
	protected abstract void endClientSession();
	
	protected abstract void endServerSession();
	
	protected abstract String executeCommand(String... inputparams);

	//getters & setters 
	
	protected String getHostname() {
		return hostname;
	}

	protected void setHostname(String hostname) {
		this.hostname = hostname;
	}

	protected long getStart_timestamp() {
		return start_timestamp;
	}

	protected void setStart_timestamp(long start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	protected long getEnd_timestamp() {
		return end_timestamp;
	}

	protected void setEnd_timestamp(long end_timestamp) {
		this.end_timestamp = end_timestamp;
	}

	protected void setPort(int port) {
		this.port = port;
	}
	
	public int getPort()
	{
		return port;
	}

	
	
}
