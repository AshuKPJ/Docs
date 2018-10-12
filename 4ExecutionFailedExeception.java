package com.bny.analytics.mlengine;

public class ExecutionFailedExeception extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExecutionFailedExeception() {
		// TODO Auto-generated constructor stub
	}

	public ExecutionFailedExeception(String msg) {
		super(msg);
	}
	
	public ExecutionFailedExeception(String message,Throwable cause)
	{
		super(message, cause);
		
	}

}
