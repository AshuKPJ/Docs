package cxf.client;


/**
 * Interface to consolidate all the commands to be executed.
 * Consumer of this webservice can decide, the coding style for consumption 
 * @author xbbncdk
 *
 */
public interface Commands {

	static int value =50;
	static String EXECUTE_COMMAND_1="ExecuteCommand||import pythonpkg as rfp";
	static String EXECUTE_COMMAND_2="ExecuteCommand||rfpSvc = rfp.RFPMain()";
	static String EXECUTE_COMMAND_3="AssignVariable||num2||" + value;
	static String EXECUTE_COMMAND_4="ExecuteCommand||output=rfpSvc.getQAns(num2=num2)";
	static String EXECUTE_COMMAND_5="GetVariable||output";
	
	interface Calculator_Commands{
		static String EXECUTE_COMMAND_1="ExecuteCommand||import pythonpackage as calpkg";
		static String EXECUTE_COMMAND_2="ExecuteCommand||calculator = calpkg.Calculator()";
		static String EXECUTE_COMMAND_3="AssignVariable||num2||" + value;
		static String EXECUTE_COMMAND_4="ExecuteCommand||output=calculator.addTen(num2=num2)";
		static String EXECUTE_COMMAND_5="GetVariable||output";
	}
	interface RFP_Commands{
		static String inputJson= "[{\"ReqID\" : 921},{\"QnString\" : \"Where are the offshore locations of your organization?\"}]";
		static String COMMAND_1="ExecuteCommand||import RFPModel_v1 as rfp";
		static String COMMAND_2="ExecuteCommand||rfpSvc = rfp.RFPMain()";
		static String COMMAND_3="AssignVariable||inputJson||" + inputJson;
		static String COMMAND_4="ExecuteCommand||output=rfpSvc.getAnsForQn(inputJson=inputJson)";
		static String COMMAND_5="GetVariable||output";
	}
}
