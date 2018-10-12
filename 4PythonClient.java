package cxf.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
	
	private static PyRequest populateCalculatorRequest() {
		PyRequest requests = new PyRequest();
		requests.addCommand(Commands.Calculator_Commands.EXECUTE_COMMAND_1);
		requests.addCommand(Commands.Calculator_Commands.EXECUTE_COMMAND_2);
		requests.addCommand(Commands.Calculator_Commands.EXECUTE_COMMAND_3);
		requests.addCommand(Commands.Calculator_Commands.EXECUTE_COMMAND_4);
		requests.addCommand(Commands.Calculator_Commands.EXECUTE_COMMAND_5);
		return requests;
	}

	/*
	 * Method invokes MLEngine Python web service Service : Interface :
	 * IPythonService Implementation : PythonService Method invoked :
	 * executeCommands Input : Request output : responses
	 * 
	 * Steps to follow while invoking MLEngine Python are : 1. Create a web
	 * client using path and Jacksonjson provider 2. Populate the request 3. Use
	 * post method on client to invoke the specified method
	 */
	public static void invokeMlEnginePython() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			//WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/PythonService", providers);
			WebClient client = WebClient.create("http://w00526n0v:8989/MLEngine/services/PythonService", providers);

			// Set input
			//PyRequest request = populateRFPRequest();
			PyRequest request = populateCalculatorRequest();
			

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

	private static PyRequest populateFRPInput() {
		PyRequest requests = new PyRequest();
		requests.addCommand(Commands.RFP_Commands.COMMAND_1);
		requests.addCommand(Commands.RFP_Commands.COMMAND_2);
		requests.addCommand(Commands.RFP_Commands.COMMAND_3);
		requests.addCommand(Commands.RFP_Commands.COMMAND_4);
		requests.addCommand(Commands.RFP_Commands.COMMAND_5);
		return requests;
	}

	public static void invokeRFP_GetAnsForQn() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			// WebClient client =
			// WebClient.create("http://localhost:8989/MLEngine/services/PythonService",
			// providers);
			// WebClient client =
			// WebClient.create("http://w00526n0v:8989/MLEngine/services/PythonService",
			// providers);

			// Set input
			PyRequest request = populateFRPInput();

			
			WebClient client = WebClient.create("http://w00526n0v:8989/MLEngine/services/PythonService", providers);

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

	private static void jsoninpt() throws JsonGenerationException, JsonMappingException, IOException {
		PyRequest request = populateCalculatorRequest();

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(request);
		System.out.println(jsonInString);
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// invoke Ml engine python
		jsoninpt();
		long start = System.currentTimeMillis();
		invokeMlEnginePython();
		// invokeRFP_GetAnsForQn();
		long end = System.currentTimeMillis();
		System.out.println("Execution Time:" + (end - start));

	}
}
