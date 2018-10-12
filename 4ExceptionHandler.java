package com.bny.analytics.mlengine;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionHandler  implements ExceptionMapper<ExecutionFailedExeception> {

	@Override
	public Response toResponse(ExecutionFailedExeception exception) {
		Response.Status status;
        status = Response.Status.INTERNAL_SERVER_ERROR;
        return Response.status(status).header("exception", exception.getMessage()).build();
	}


	

}
