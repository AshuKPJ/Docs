package cxf.client;

import com.bny.analytics.mlengine.PyExecutor;

public class ClientSocket {

	/**
	 * Invoking multiple clients sequentially 
	 * Make sure you end the connection
	 * @param args
	 */
	public static void main(String[] args) {
		
		PyExecutor pyexecutor=new PyExecutor(8189);
		pyexecutor.startSession();
		pyexecutor.executeCommand("ExecuteCommand||import pythonpkg as rfp");
		pyexecutor.executeCommand("ExecuteCommand||rfpSvc = rfp.RFPMain()");
		pyexecutor.executeCommand("AssignVariable||num2||" + 50);
		pyexecutor.executeCommand("ExecuteCommand||output=rfpSvc.getQAns(num2=num2)");
		String output =pyexecutor.executeCommand("GetVariable||output");
		System.out.println("Final output received is client 1 "+ output);
		pyexecutor.endServerSession();
		System.out.println("starting client 2");
		PyExecutor pyexecutor2=new PyExecutor(8189);
		pyexecutor2.startSession();
		pyexecutor2.executeCommand("ExecuteCommand||import pythonpkg as rfp");
		pyexecutor2.executeCommand("ExecuteCommand||rfpSvc = rfp.RFPMain()");
		pyexecutor2.executeCommand("AssignVariable||num2||" + 50);
		pyexecutor2.executeCommand("ExecuteCommand||output=rfpSvc.getQAns(num2=num2)");
		 output =pyexecutor.executeCommand("GetVariable||output");
		 pyexecutor2.endServerSession();
		System.out.println("Final output received is client 2 "+ output);
		
		System.out.println("starting client 3");
		PyExecutor pyexecutor3=new PyExecutor(8189);
		pyexecutor3.startSession();
		pyexecutor3.executeCommand("ExecuteCommand||import pythonpkg as rfp");
		pyexecutor3.executeCommand("ExecuteCommand||rfpSvc = rfp.RFPMain()");
		pyexecutor3.executeCommand("AssignVariable||num2||" + 50);
		pyexecutor3.executeCommand("ExecuteCommand||output=rfpSvc.getQAns(num2=num2)");
		 output =pyexecutor.executeCommand("GetVariable||output");
		 pyexecutor3.endServerSession();
		System.out.println("Final output received is client 3 "+ output);
		System.out.println("3 clients bound to same python socket!!");
	}
}
