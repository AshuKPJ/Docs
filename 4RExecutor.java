package com.bny.analytics.mlengine;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.bny.analytics.mlengine.utils.Constants;

public class RExecutor extends Executor {

	final static Logger logger = Logger.getLogger(RExecutor.class);

	private Process rserve = null;
	private RConnection clientConn = null;

	/*
	 * Constructor
	 */
	public RExecutor(int port) {
		super(port);
		initiateRServe();
	}

	/*
	 * Initiates RServe for the specified port
	 */
	private void initiateRServe() {
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		try {

			// String command="H:\\R\\R-3.3.2\\bin\\R.exe CMD BATCH --no-save
			// --no-restore \"--args "+ port + "\" " +
			// "C:\\reference\\InstantiateRServe.R outfile_" + port + ".out";
			String command = rb.getString(Constants.REXE_LOCATION) + Constants.SPACE + rb.getString(Constants.BATCH_CMD)
					+ Constants.SPACE + "\"--args " + getPort() + "\" " + rb.getString(Constants.RSERVE_LOCATION)
					+ Constants.SPACE + rb.getString(Constants.RSERVE_OUTPUT_LOCATION) + "outfile_" + getPort()
					+ ".out";

			logger.info("R Serve Command: " + command);
			long startTime=System.currentTimeMillis();
			rserve = Runtime.getRuntime().exec(command);
			long endTime=System.currentTimeMillis();
			logger.info("Time to instantiate RServe : " + (endTime-startTime));

		} catch (IOException e) {
			logger.fatal("Error Occured while instantiating RServe for port number " + getPort());
			logger.info(e.getMessage());
		}

	}

	/*
	 * Kills the rServe process
	 */
	@Override
	public void kill() {
		try {
			clientConn.shutdown();
		} catch (RserveException e) {
			logger.error("Error occured while shutting down the Rserve");
			logger.info(e.getStackTrace());
		}
		setEnd_timestamp(System.currentTimeMillis());
	}

	/*
	 * Creates a rConnection with RServe
	 */
	@Override
	public void startSession() {
		long startTimestamp = System.currentTimeMillis();
		while (clientConn == null) {
			try {

				clientConn = new RConnection(getHostname(), getPort());
				logger.info("R Client Connnection created for port: " + getPort());
			} catch (RserveException e) {
				logger.error("Error occurred while creating RConnection with RServe for port: " + getPort());
				logger.error(e.getMessage());
			}
		}
		long endTimestamp = System.currentTimeMillis();
		logger.info("Time taken for fetching Client connection: " + (endTimestamp - startTimestamp));

	}

	/*
	 * Ends session
	 */

	@Override
	public void endClientSession() {
		logger.info("Inside R endClientSession");
		if (clientConn.isConnected() == true) {
			clientConn.close();
			clientConn=null;
			logger.info("Only RConnnection closed");
			logger.info("RServe is still  live for port number :" + getPort());
		}
	}

	@Override
	public void endServerSession() {
		logger.info("Inside R endSession");
		if (clientConn.isConnected() == true) {
			kill();
			logger.info("RServe and RConnnection closed");
			logger.info("RServe was live for : " + (getEnd_timestamp() - getStart_timestamp()) + " for port number "
					+ getPort());
		}

	}

	@Override
	public String executeCommand(String... inputparams) {
		REXP result = null;
		String cmd = inputparams[0];
		String inputasjson = inputparams[1];
		logger.info("Inside executeCommand of RExecutor");
		REXP input = new REXPString(inputasjson);
		try {
			logger.info("clientConnection: " + clientConn);
			long initstartTime=System.currentTimeMillis();
			clientConn.assign("input", input);
			clientConn.eval(cmd.replace(";", "\n").toString());
			result = clientConn.eval("output");
			logger.info(result.getClass());
			long extracommandsStart =System.currentTimeMillis();
			clientConn.eval("rm(list=ls())");
			clientConn.eval("graphics.off()");
			long initendTime=System.currentTimeMillis();
			long diff=initendTime-initstartTime;
			logger.info("Execution complete");
			logger.info("Time taken by execute extra commands: " + (extracommandsStart-initendTime));
			logger.info("Time taken by execute command: " + diff);

		} catch (RserveException e) {

			
			logger.error("Ërror occurred while executing R command");
			logger.error("Error msg: " + e.getMessage());
			logger.error("Error code: " + e.getRequestReturnCode());
			logger.error("Error description: " + e.getRequestErrorDescription());
			logger.info("command :" + cmd);
			logger.info("input: " + inputasjson);
			
			if(e.getRequestReturnCode()==127)
			{
				logger.info("R exception 127 occured");
				try {
					//clientConn.eval("a<-paste(unlist(traceback()),collapse = '\n')");
					//REXP result2 = clientConn.eval("a");
					clientConn.eval("a<-geterrmessage()");
					REXP result2 = clientConn.eval("a");
					try {
						logger.info(result2.asString());
						return "";
					} catch (REXPMismatchException e1) {
						// TODO Auto-generated catch block
						logger.info("Ëxception occured while fetching R stack trace");
						logger.info(e1.getStackTrace());
					}
					
				} catch (RserveException e1) {
					// TODO Auto-generated catch block
					logger.info("Ëxception occured while fetching R stack trace");
					logger.info(e1.getStackTrace());
				}
			}

		}

		try {
			return result.asString();
		} catch (REXPMismatchException e) {
			logger.error("Result returned from RServe could not be translated into java String");
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
		}
		logger.info("Returning null from Rexecutor.executeCommand");
		// Technically this shouldn't happen , but in case it does check
		return null;
	}

}
