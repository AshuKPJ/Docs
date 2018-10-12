/**
 * Class : PyExecutor
 * Purpose : PyExecutor is responsible for establishing connection with python 
 *           socket serve.
 * Constructor : To be initialized with hostname and port number. default:(localhost,port)  
 * To Start session with sock serve : startSession
 * To End session with sock serve : endSession
 * To execute a command on sock serve : executeCommand
 *   
 */
package com.bny.analytics.mlengine;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.bny.analytics.mlengine.utils.Constants;

public class PyExecutor extends Executor {

	final static Logger logger = Logger.getLogger(PyExecutor.class);

	private Socket clientSocket = null;
	private Process pyServe;

	
	// Constructor
	public PyExecutor(int port) {
		super(port);
		initiatePyServe();
	}

	/*
	 * Instantiates sockServe. Fetches the sockServe location from
	 * config.properties populates command by concatinating python, sockserve
	 * file and port number then using Runtime API executes the command and
	 * stores the returned reference into pyServe class variable for future use
	 */
	public void initiatePyServe() {
		try {
			ResourceBundle rb = ApplicationContextListener.getResourceBundle();
			String PyServeLocation = rb.getString(Constants.PYTHONSOCKSERVE_LOCATION);

			// populates command as "python <sockserver python file> <port>"
			String command = Constants.PYTHON + Constants.SPACE + PyServeLocation + Constants.SPACE + this.getPort();
			logger.info(command);
			pyServe = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			logger.fatal("Error Occured while instantiating PyServe for port number:" + getPort());
			logger.fatal(e.getMessage());
		}
	}

	/*
	 * Kills the pyServe process
	 */
	public void kill() {
		pyServe.destroy();
		setEnd_timestamp(System.currentTimeMillis());
	}

	/*
	 * Creates a socket connection between python sockserve
	 */
	public void startSession() {
		try {
			clientSocket = new Socket(getHostname(), getPort());
			clientSocket.setKeepAlive(true);
			logger.info("Client socket initialized with port number :" + getPort());
		} catch (IOException e) {

			logger.error("Error occurred while creating client socket");
			logger.error("Unable to create client socket for port:" + getPort());
			logger.error(e.getMessage());
		}

	}

	@Override
	protected void endClientSession() {
		// TODO Auto-generated method stub

		if (clientSocket.isConnected() == true) {
			try {
				clientSocket.close();
				logger.info("Client Socket closed");
				logger.info("Pyserve is still live for port number " + getPort());
				;
			} catch (IOException e) {
				logger.error("Error occurred while closing client socket with port number " + getPort());
				logger.error(e.getMessage());
			}
		}
	}

	/*
	 * Closes the connection with sockserve further killing the sockServe as
	 * well.
	 */
	public void endServerSession() {

		if (clientSocket.isConnected() == true) {
			try {
				
				clientSocket.close();
				kill();
				logger.info("Client Socket closed");
				logger.info("Pyserve was live for : " + (getEnd_timestamp() - getStart_timestamp())
						+ " for port number " + getPort());
				;
			} catch (IOException e) {
				logger.error("Error occurred while closing client socket");
				logger.error(e.getMessage());
			}
		}

	}

	/*
	 * Method sends the data to the sock Serve. Command is suffixed with ~~ to
	 * indicate end of command to sock serve
	 */
	private void sendDataToSocket(String cmd) {

		if (clientSocket != null) {
			try {
				if (clientSocket.isConnected()) {
					// DataOutputStream out = new
					// DataOutputStream(clientSocket.getOutputStream());
					// byte[] writeData = new byte[1024];
					//cmd = cmd + "~~";
					byte[] writeData = cmd.getBytes();
					byte[] data = writeData;

					// int startCount=0;
					// int endCount=1024;
					// System.out.println("total data length in bytes" +
					// writeData.length);
					// while(writeData.length>0)
					// {
					// if(writeData.length>1024)
					// endCount=1024;
					// else
					// endCount=writeData.length;
					//
					// byte[] slice = Arrays.copyOfRange(writeData, 0,
					// endCount);
					//
					// out.write(slice);
					// out.flush();
					// System.out.println("data sent: " + new String(slice));
					// startCount=startCount+ slice.length;
					//
					// System.out.println("data sent: "+ endCount);
					//
					// writeData =Arrays.copyOfRange(data,
					// startCount,data.length);
					// }

					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					out.print(cmd + "~~");
					out.flush();
					logger.info("command sent to python socket");
				}

			} catch (IOException e) {
				logger.error("Error occurred sending the data to the socket");
				logger.error(e.getMessage());
			}
		} else {
			logger.error("client socket is null !! looks like there was an error creating client socket");
		}

	}

	/*
	 * Method receives data from the sock serve. It reads line by line using
	 * buffered reader and breaks the loop when end of msg is reached i.e. ~~.
	 */
	private String getDataFromSocket() {
		DataInputStream in = null;
		String output_str = "";
		byte[] readData = new byte[1024];

		try {
			// in = new BufferedReader(new
			// InputStreamReader(clientSocket.getInputStream()));
			in = new DataInputStream(clientSocket.getInputStream());

			while (true) {
				// output_str = output_str + in.readLine();
				int dataRead = in.read(readData);
				output_str = output_str + new String(readData, 0, dataRead);
				//System.out.println("data received : " + new String(readData, 0, dataRead));
				if (output_str.endsWith("~~\r\n"))
					break;
			}
			output_str = output_str.replace("~~\r\n", "");
			logger.info("data received from server : " + output_str);
		} catch (IOException ex) {
			logger.error("Error occurred sending the data to the socket");

			logger.error(ex.getMessage());
			// TODO : assuming its a network issue
		} catch (Exception e) {
			logger.error("Error occurred while getting the data from the socket");
			logger.error(e.getMessage());

		}

		return output_str;

	}

	/*
	 * Method executes the command passed as the parameter. It futher calls
	 * sendDataToSocket to send the command to sockserve and gets the result
	 * from the sock serve
	 */
	@Override
	public String executeCommand(String... inputparams) {
		String cmd = inputparams[0];
		logger.info("command :" + cmd);
		sendDataToSocket(cmd);
		String output_stream = getDataFromSocket();
		logger.info("Command successfully executed");
		return (output_stream);
	}

}
