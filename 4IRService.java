package com.bny.analytics.mlengine;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bny.analytics.mlengine.objs.RRequest;
import com.bny.analytics.mlengine.objs.RResponse;

@Path("/RService/")
public interface IRService {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public RResponse executeCommand(RRequest input);
}
