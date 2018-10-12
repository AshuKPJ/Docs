package com.bny.analytics.coststatementvalidator.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class Response {

	private String output;
	
	public Response() {
		// TODO Auto-generated constructor stub
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
}
