package com.bny.analytics.mlengine;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bny.analytics.mlengine.objs.PyRequest;
import com.bny.analytics.mlengine.objs.PyResponse;

@Path("/PythonService/")
public interface IPythonService {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public PyResponse executeCommands(PyRequest input) throws ExecutionFailedExeception;

}
