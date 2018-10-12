package com.bny.analytics.mlengine;

import org.apache.log4j.Logger;

import com.bny.analytics.mlengine.objs.RRequest;
import com.bny.analytics.mlengine.objs.RResponse;

public class RService implements IRService {

	final static Logger logger = Logger.getLogger(RService.class);

	@Override
	public RResponse executeCommand(RRequest input) {
		logger.info("Inside RService:executeCommand");

		// start session
		Executor executor = RPooledObject.getObject();
		executor.startSession();

		// executeCommand
		RResponse rResponse = new RResponse();
		String response = null;

		response = executor.executeCommand(input.getCmd(), input.getInputAsJson());

		RPooledObject.releaseObject(executor);
		rResponse.setOutput(response);
		return rResponse;
	}

}
