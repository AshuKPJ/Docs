package com.bny.analytics.mlengine.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class Response {
	private String output;
	
	public Response() {
		// TODO Auto-generated constructor stub
	}

	public Response(String output)
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
