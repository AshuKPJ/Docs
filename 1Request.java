package com.bny.analytics.coststatementvalidator.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class Request {


	private String inputAsJson;

	public String getInputAsJson() {
		return inputAsJson;
	}
	public void setInputAsJson(String inputAsJson) {
		this.inputAsJson = inputAsJson;
	}
	
	
}
