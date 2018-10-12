package com.bny.analytics.coststatementvalidator.objs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "PyRequest")
public class PyRequest {

	List<String> commands=new ArrayList<String>();

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	
	public void addCommand(String command)
	{
		this.commands.add(command);
	}
}
