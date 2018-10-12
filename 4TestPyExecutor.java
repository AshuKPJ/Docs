/*
 * Test class for testing PyExecutor.
 * To be removed latter,
 */

package com.bny.analytics.mlengine;

import java.util.ResourceBundle;

public class TestPyExecutor {

	
	public static void checkResourceBundle()
	{
		ResourceBundle rb = ResourceBundle.getBundle("com.bny.analytics.mlengine.config");
		System.out.println(rb.getString("pythonSockServe_location"));
	}
	public static void main(String[] args) {
		
		System.out.println("Checking python client...");
		PyExecutor pyexecutor=new PyExecutor(8189);
		pyexecutor.startSession();
		pyexecutor.executeCommand("ExecuteCommand||import pythonpkg as rfp");
		pyexecutor.executeCommand("ExecuteCommand||rfpSvc = rfp.RFPMain()");
		pyexecutor.executeCommand("AssignVariable||num2||" + 50);
		pyexecutor.executeCommand("ExecuteCommand||output=rfpSvc.getQAns(num2=num2)");
		String output =pyexecutor.executeCommand("GetVariable||output");
		System.out.println("Final output received is "+ output);
		pyexecutor.endServerSession();
	}
}
