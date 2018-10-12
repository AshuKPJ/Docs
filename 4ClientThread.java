package cxf.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import com.bny.analytics.mlengine.objs.PyRequest;
import com.bny.analytics.mlengine.objs.PyResponse;

public class ClientThread extends Thread {

	private PyRequest populateRFPRequest() {
		PyRequest requests = new PyRequest();
		requests.addCommand(Commands.EXECUTE_COMMAND_1);
		requests.addCommand(Commands.EXECUTE_COMMAND_2);
		requests.addCommand(Commands.EXECUTE_COMMAND_3);
		requests.addCommand(Commands.EXECUTE_COMMAND_4);
		requests.addCommand(Commands.EXECUTE_COMMAND_5);
		return requests;
	}
	
	private void invokeMlEnginePython() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());
			System.out.println("sleep..");
		
			WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/PythonService", providers);
			
			// Set input
			PyRequest request = populateRFPRequest();
			Thread.currentThread().sleep(100);
			
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
	
	
	public void run() {
		System.out.println("Executing thread...");
		invokeMlEnginePython();
	}
	
}
