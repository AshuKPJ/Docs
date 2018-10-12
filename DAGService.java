package com.bny.analytics.dagemailrouting;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import com.bny.analytics.emailrouting.objs.Request;
import com.bny.analytics.emailrouting.objs.Response;
import com.bny.analytics.emailrouting.objs.RRequest;
import com.bny.analytics.emailrouting.objs.RResponse;

public class DAGService implements IDAGService {

	final static Logger logger = Logger.getLogger(DAGService.class);
	//final static String cmd="library(DAGEmailRouting);output <- TrainModel(input);";
	final static String cmd="library(DAGEmailContent);output <- TrainModel(input);";
	final static String MlENGINE_RSERVICE="http://w00526n0v:8989//MLEngine/services/RService";

	private RRequest populateRequest(Request input) {
		RRequest request = new RRequest();
		request.setCmd(cmd);
		request.setInputAsJson(input.getInputAsJson());
		return request;
	}
	
	
	public String invokeMlEngineRService(Request input) {
		String response="";
		try {
			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			WebClient client = WebClient.create(MlENGINE_RSERVICE, providers);
			// Set input
			RRequest request = populateRequest(input);

			// post request
			client = client.accept("application/json").type("application/json");
			javax.ws.rs.core.Response r = client.post(request);
			 RResponse resp = r.readEntity(RResponse.class);
			 response=resp.getOutput();

		} catch (Exception e) {
			System.out.println("Exception occured while invoking Ml Engine R");
			e.printStackTrace();
		}
		return  response;

	}
	
	@Override
	public Response executeCommand(Request input) {

		logger.info("Invoking MlEngine RService on " + MlENGINE_RSERVICE);
		Response Response = new Response();
		long startTime=System.currentTimeMillis();
		String response = invokeMlEngineRService(input);
		long endTime=System.currentTimeMillis();
		Response.setOutput(response);
		logger.info("Response received from MLEngine RService in " + (endTime-startTime) + " ms");
		return Response;
	}

	

}
