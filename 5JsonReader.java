package com.bny.analytics.mlengine.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bny.analytics.mlengine.objs.RRequest;

public class JsonReader {

	
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
		System.out.println(jsonObject.toJSONString());
		inputAsJson=jsonObject.toJSONString();
		return inputAsJson;
	}
	
	public static void convertInputToJson()
	{
		RRequest input= populateRequest();
		ObjectMapper mapper=new ObjectMapper();
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(input);
			System.out.println(jsonInString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
//		convertInputToJson();
//		String filename="C:\\MLEngine\\workspace\\MlEngineInvokerSample\\src\\com\\bny\\analytics\\mlengine\\client\\temp2new.JSON";
//		readJsonFile(filename);
		
		String filename="C:\\Users\\xbbncdk\\Desktop\\input.JSON";
	
		readJsonFile(filename);
		System.out.println("Execution complete");
	}
}
