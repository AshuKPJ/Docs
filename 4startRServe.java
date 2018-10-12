package cxf.client;

import java.io.IOException;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class startRServe {

	public static Process invokeRServe() throws IOException {
		int port = 8501;
		String command = "H:\\R\\R-3.3.2\\bin\\R.exe CMD BATCH --no-save --no-restore \"--args " + port + "\" "
				+ "C:\\reference\\InstantiateRServe.R outfile_" + port + ".out";
		Process rserve = Runtime.getRuntime().exec(command);
		RConnection rconn=null;
		while(rconn==null)
		{
	
		 try {
			rconn = new RConnection("localhost", port);
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			System.out.println("RserveExecption");
		}
		}
		
		//rconn.close();
		
		try {
			//rconn.detach();
			rconn.shutdown();
			//rconn.serverShutdown();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			System.out.println("exception while detaching the connection !!");
			e.printStackTrace();
		}
		rconn.close();
		return rserve;

	}

	public static void main(String[] args) throws RserveException {

		Process rserve = null;
		try {
			rserve = invokeRServe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Now closing the connection..");
		rserve.destroy();
		// rserve.destroyForcibly();
		System.out.println("completed");
	}
}
