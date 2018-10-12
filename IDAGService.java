package com.bny.analytics.dagemailrouting;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bny.analytics.emailrouting.objs.Request;
import com.bny.analytics.emailrouting.objs.Response;

@Path("/DAGService/")
public interface IDAGService {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response executeCommand(Request input);
}
