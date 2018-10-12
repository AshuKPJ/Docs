package cxf.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import com.bny.analytics.mlengine.objs.RRequest;
import com.bny.analytics.mlengine.objs.RResponse;

public class RClientMultiThreading extends Thread {

	private static RRequest populateRequest() {
		RRequest request = new RRequest();
		String cmd="library(datasets);output <- toupper(input);";
		String inputAsJson="India";
		request.setCmd(cmd);
		request.setInputAsJson(inputAsJson);
		return request;
	}

	public static void invokeMlEngineRService() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/RService", providers);
			//WebClient client = WebClient.create("http://w00526n0v:8989//MLEngine/services/RService", providers);
			// Set input
			RRequest request = populateRequest();

			// post request
			client = client.accept("application/json").type("application/json");
			javax.ws.rs.core.Response r = client.post(request);
			RResponse resp = r.readEntity(RResponse.class);

			System.out.println("Execution Complete...");
			System.out.println(resp.getOutput());
		} catch (Exception e) {
			System.out.println("Exception occured while invoking Ml Engine R");
			e.printStackTrace();
		}

	}
	
	public void run() {
		System.out.println("Executing thread...");
		invokeMlEngineRService();
	}
	
}
