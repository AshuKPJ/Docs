package com.bny.analytics.mlengine.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bny.analytics.mlengine.objs.RRequest;
import com.bny.analytics.mlengine.objs.RResponse;

public class RServiceClient {

	/**
	 * populateRequest : This method populates RRequest object which is an input
	 * for the MlEngine Service.One can modify the commands within the RRequest
	 * object as per requirement.The commands have to be separated with
	 * semi-colon.
	 */
	
	private static RRequest populateRequest() {
		RRequest request = new RRequest();
		String cmd="library(datasets);output <- toupper(input);";
		String inputAsJson="India";
		request.setCmd(cmd);
		request.setInputAsJson(inputAsJson);
		return request;
	}
	
	private static String readJsonFile(String fileLocation) {
		String inputAsJson = null;
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) obj;
		
		inputAsJson=jsonObject.toJSONString();
		return inputAsJson;
	}
	
	/*
	 * Method to check stataAssist function Recom.ct
	 */
	private static RRequest populateStatAssist()
	{
		RRequest request = new RRequest();
		String cmd="library(STATAssist);output <- Recom.ct(input);";
		String filename="C:\\MLEngine\\workspace\\MlEngineInvokerSample\\src\\com\\bny\\analytics\\mlengine\\client\\temp2new.JSON";
		String inputAsJson=readJsonFile(filename);
		request.setCmd(cmd);
		request.setInputAsJson(inputAsJson);
		return request;
	}

	/**
	 * invokeMlEngineRService : invokes the MlEngine R service. 
	 * Steps to be followed while invoking MlEngine R Service are :
	 * 
	 * 1. Create a web Client passing the appropriate url for the web service 
	 *    usually the path will be http://<hostname:port>/MlEngine/services/RService
	 *    and JacsonJaxbJsonProvider as parameters.
	 * 2. Then populate RRequest input with the required R commands. 
	 * 3. Specify the format passed and accepted by the client.In our case its JSON for both
	 * 4. use post method on client passing populated RRequest object and fetch the response
	 * 5. read response in RResponse format using readEntity method from the above fetched object.
	 *   
	 */
	
	public static void invokeMlEngineRService() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			//WebClient client = WebClient.create("http://localhost:8989/MLEngine/services/RService", providers);
			WebClient client = WebClient.create("http://w00526n0v:8989//MLEngine/services/RService", providers);
			// Set input
			//RRequest request = populateRequest();
			RRequest request = populateStatAssist();
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

	public static void main(String[] args) {
		invokeMlEngineRService();
	}
}
