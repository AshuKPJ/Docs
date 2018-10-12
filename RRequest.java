package com.bny.analytics.emailrouting.objs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RRequest")
public class RRequest {

	private String cmd;
	private String inputAsJson;
	


	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getInputAsJson() {
		return inputAsJson;
	}
	public void setInputAsJson(String inputAsJson) {
		this.inputAsJson = inputAsJson;
	}
	
	
}
