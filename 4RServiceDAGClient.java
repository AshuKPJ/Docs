package cxf.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.bny.analytics.mlengine.objs.RRequest;
import com.bny.analytics.mlengine.objs.RResponse;

public class RServiceDAGClient {
	
	private static String writeInput(RRequest input) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(input);
		System.out.println(jsonInString);
		return jsonInString;
		
	}

	private static String readFile() {
		BufferedReader reader;
		String json = "";
		
		
		try {
			reader = new BufferedReader(new FileReader("C:\\WorkDone\\InputSpecial.txt"));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
			json = sb.toString();
			reader.close();
		} 
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		System.out.println(json);
		return json;
		
	}

	private static RRequest populateRequest() {
		RRequest request = new RRequest();
		String cmd = "library(DAGEmailRouting);output <- TrainModel(input);";
		String inputAsJson = readFile();

		request.setCmd(cmd);
		request.setInputAsJson(inputAsJson);
		return request;
	}

	public static void invokeMlEngineRService() {
		try {

			List<Object> providers = new ArrayList<Object>();
			providers.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());

			// WebClient client =
			// WebClient.create("http://localhost:8989/MLEngine/services/RService",
			// providers);
			WebClient client = WebClient.create("http://w00526n0v:8989//MLEngine/services/RService", providers);
			// Set input
			RRequest request = populateRequest();
			writeInput(request);

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
		long startTime=System.currentTimeMillis();
		invokeMlEngineRService();
		long endTime=System.currentTimeMillis();
		System.out.println("Time taken for execution:" + (endTime-startTime));
		
	}
}
