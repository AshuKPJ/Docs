package com.bny.analytics.mlengine;

import org.apache.log4j.Logger;

import com.bny.analytics.mlengine.objs.PyRequest;
import com.bny.analytics.mlengine.objs.PyResponse;

public class PythonService implements IPythonService {

	final static Logger logger = Logger.getLogger(PythonService.class);

	/*
	 * Method accepts Request as an input which is nothing but list of commands
	 * Returns Output of last command as Response.
	 */

	@Override
	public PyResponse executeCommands(PyRequest input) throws ExecutionFailedExeception{
		logger.info("Inside PythonService:executeCommands");
		
		//start session
		Executor executor = PyPooledObject.getObject();
		executor.startSession();
		
		//executeCommand
		PyResponse reponses = new PyResponse();
		String response = null;
		
		for (String command : input.getCommands()) {
			response = executor.executeCommand(command);
			
			//Code to be uncommented if exception is to be raised from ML Engine [also uncomment entry in cfx] 
//			if(response.equals("Execution Failed"))
//			{
//				System.out.println("Execution failed in python.. raising exception");
//				throw new ExecutionFailedExeception("Execution Failed in python for command: " + command);
//			}
		}
		PyPooledObject.releaseObject(executor);
		reponses.setOutput(response);
		return reponses;
	}
}
