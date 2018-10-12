package com.bny.analytics.coststatementvalidator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bny.analytics.coststatementvalidator.objs.Request;
import com.bny.analytics.coststatementvalidator.objs.Response;

@Path("/ValidatorService/")
public interface IValidatorService {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response validate(Request input);
}
