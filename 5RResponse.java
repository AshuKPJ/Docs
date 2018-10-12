package com.bny.analytics.mlengine.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RResponse")
public class RResponse {

	private String output;
	
	public RResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
}
