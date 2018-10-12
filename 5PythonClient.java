package com.bny.analytics.mlengine.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import com.bny.analytics.mlengine.objs.PyRequest;
import com.bny.analytics.mlengine.objs.PyResponse;

/**
 * Sample client code for consuming MLEngine Python as a web service Using Web
 * Client
 * 
 * @author xbbncdk
 *
 */
public class PythonClient {

	/*
	 * Method populates the request object which is to be sent as a parameter to
	 * the webservice. Request is nothing but list of string objects i.e.
	 * Commands
	 */
	private static PyRequest populateRFPRequest() {
		PyRequest requests = new PyRequest();
		requests.addCommand(Commands.EXECUTE_COMMAND_1);
		requests.addCommand(Commands.EXECUTE_COMMAND_2);
		requests.addCommand(Commands.EXECUTE_COMMAND_3);
		requests.addCommand(Commands.EXECUTE_COMMAND_4);
		requests.addCommand(Commands.EXECUTE_COMMAND_5);
		return requests;
	}
	
	/**
	 * invokeMlEnginePython : invokes the MlEngine Python service. 
	 * Steps to be followed while invoking MlEngine python Service are :
	 * 
	 * 1. Create a web Client passing the appropriate url for the web service 
	 *    usually the path will be http://<hostname:port>/MlEngine/services/PythonService
	 *    and JacsonJaxbJsonProvider as parameters.
	 * 2. Then populate PyRequest input with the required python commands. 
	 * 3. Specify the format passed and accepted by the client.In our case its JSON for both
	 * 4. use post method on client passing populated PyRequest object and fetch the response
	 * 5. read response in RResponse format using readEntity method from the above fetched object.
	 *   
	 */
	
	public static void invokeMlEnginePython() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());
			
			WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/PythonService", providers);
			
			// Set input
			PyRequest request = populateRFPRequest();

			// post request
			client = client.accept("application/json").type("application/json");
			javax.ws.rs.core.Response r = client.post(request);
			PyResponse resp = r.readEntity(PyResponse.class);

			System.out.println("Execution Complete...");
			System.out.println(resp.getOutput());
		} catch (Exception e) {
			System.out.println("Exception occured while invoking Ml Engine Pyhton");
			e.printStackTrace();
		}

	}
	
	private static PyRequest populateFRPInput()
	{
		PyRequest requests = new PyRequest();
		requests.addCommand(Commands.RFP_Commands.COMMAND_1);
		requests.addCommand(Commands.RFP_Commands.COMMAND_2);
		requests.addCommand(Commands.RFP_Commands.COMMAND_3);
		requests.addCommand(Commands.RFP_Commands.COMMAND_4);
		requests.addCommand(Commands.RFP_Commands.COMMAND_5);
		return requests;
	}
	public static void invokeRFP_GetAnsForQn()
	{
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			//WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/PythonService", providers);
			WebClient client = WebClient.create("http://w00526n0v:8989/MLEngine/services/PythonService", providers);

			// Set input
			PyRequest request = populateFRPInput();

			// post request
			client = client.accept("application/json").type("application/json");
			javax.ws.rs.core.Response r = client.post(request);
			PyResponse resp = r.readEntity(PyResponse.class);

			System.out.println("Execution Complete...");
			System.out.println(resp.getOutput());
		} catch (Exception e) {
			System.out.println("Exception occured while invoking Ml Engine for RFP");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// invoke Ml engine python
		long start =System.currentTimeMillis();
		invokeMlEnginePython();
		//invokeRFP_GetAnsForQn();
		long end=System.currentTimeMillis();
		System.out.println("Execution Time:" + (end-start));

	}
}
