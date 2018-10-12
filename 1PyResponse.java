package com.bny.analytics.coststatementvalidator.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PyResponse")
public class PyResponse {
	
	private String output;
	
	public PyResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public PyResponse(String output)
	{
		this.output=output;
	}
	
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	
}
