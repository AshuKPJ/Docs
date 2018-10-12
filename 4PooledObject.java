package com.bny.analytics.mlengine;

import org.apache.log4j.Logger;

public class PooledObject {

	final static Logger logger = Logger.getLogger(PooledObject.class);
	private int port;
	private Process process;

	public PooledObject(int port) {
		this.port = port;
	}

	public PooledObject(int port, Process process) {
		this.port = port;
		this.process = process;
	}
	
	/*
	 * Method destroys the given instance
	 */
	private void kill()
	{
		process.destroy();
	}
	

}
