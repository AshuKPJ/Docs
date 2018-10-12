package com.bny.analytics.coststatementvalidator;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import com.bny.analytics.coststatementvalidator.objs.PyRequest;
import com.bny.analytics.coststatementvalidator.objs.PyResponse;
import com.bny.analytics.coststatementvalidator.objs.Request;
import com.bny.analytics.coststatementvalidator.objs.Response;

public class ValidatorService implements IValidatorService {

	
	final static Logger logger = Logger.getLogger(ValidatorService.class);

	// TODO : validate and correct
	final static String MlENGINE_PYSERVICE = "http://wtpcpavpnq02:8080/MLEngine/services/PythonService";

	/*
	 * Method populates PyRequest 
	 */
	private PyRequest populateRequest(Request request) {
		PyRequest pyRequest = new PyRequest();
		// TODO : complete, Note : commands hard coded 
		List<String> commands = new ArrayList<>();

		commands.add("AssignVariable||js_str||" + request.getInputAsJson());
		commands.add("ExecuteCommand||from PIASS_Cost_Stmt_PDF_Data_Validation import PIASS_Cost_Stmt_PDF_Data_Validation as pc");
		commands.add("ExecuteCommand||p=pc.PIASS_Cost_Stmt_PDF_Data_Validation(js_str)");
		commands.add("ExecuteCommand||output=p.Run_ML()");
		commands.add("GetVariable||output");
		
		pyRequest.setCommands(commands);
		return pyRequest;
	}


	/*
	 * Method invokes MlEngine's python service 
	 */
	private String invokeMlEnginePyService(Request input) {
		String response="";
		try {
			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			WebClient client = WebClient.create(MlENGINE_PYSERVICE, providers);
			// Set input
			PyRequest pyRequest = populateRequest(input);

			// post request
			client = client.accept("application/json").type("application/json");
			javax.ws.rs.core.Response r = client.post(pyRequest);
			PyResponse resp = r.readEntity(PyResponse.class);
			 response=resp.getOutput();

		} catch (Exception e) {
			logger.error("Exception occured while invoking Ml Engine Python Service ");
			logger.info(e.getStackTrace());
		}
		return  response;

	}
	
	@Override
	public Response validate(Request input) {
		logger.info("Invoking MlEngine PyService on " + MlENGINE_PYSERVICE);
		Response response=new Response();
		long startTime=System.currentTimeMillis();
		String output=invokeMlEnginePyService(input);
		long endTime=System.currentTimeMillis();
		response.setOutput(output);
		logger.info("Response received from MLEngine PythonService in " + (endTime-startTime) + " ms");
		return response;
	}

	
//	@Override
//	public Response executeCommand(Request input) {
//
//		Response Response = new Response();
//		String response = "Sucess";
//		Response.setOutput(response);
//		return Response;
//	}

}
