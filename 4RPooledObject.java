package com.bny.analytics.mlengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.bny.analytics.mlengine.utils.Constants;

public class RPooledObject {

	final static Logger logger = Logger.getLogger(RPooledObject.class);

	private static Set<Integer> available_port = Collections.synchronizedSet(new HashSet<Integer>());
	private static Set<Integer> inUse_port = Collections.synchronizedSet(new HashSet<Integer>());

	private static Map<Integer, Executor> always_available = new HashMap<Integer, Executor>();

	private RPooledObject() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Initialize the available pool
	 */
	protected static void initializePool() {
		logger.info("Initializing available R Pool");
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		int start_port = Integer.valueOf(rb.getString(Constants.R_START_PORT));
		int end_port = Integer.valueOf(rb.getString(Constants.R_END_PORT));
		for (int count = start_port; count <= end_port; count++) {
			available_port.add(count);
		}

		logger.info("Initialized the complete available pool from port " + start_port + " to " + end_port);
		logger.info("Total number of ports available are: " + available_port.size());

		initializeAlwaysOpenPool();
	}

	/*
	 * Destroying the available RServe pool
	 */
	protected static void destroyPool() {
		logger.info("Destroying available R Pool");
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		String hostname=rb.getString(Constants.HOSTNAME);
		for(Entry<Integer, Executor> entry:always_available.entrySet())
		{
			int port =entry.getKey();
			Executor executor=entry.getValue();
			RConnection rcon;
			try {
				rcon = new RConnection(hostname, port);
				rcon.shutdown();
			} catch (RserveException e) {
				logger.error("Execption occurred while shutting down RServe with port :"+ port);
				logger.info(e.getStackTrace());
			}
		}
		logger.info("Destruction of RServe always open pool completed");
	}

	private static void initializeAlwaysOpenPool() {
		logger.info("Initializing always available R Pool");
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		int always_open = Integer.valueOf(rb.getString(Constants.R_ALWAYS_OPEN));

		int count = 0;
		Iterator<Integer> iter = available_port.iterator();
		while (count < always_open && iter.hasNext()) {
			int port = iter.next();
			Executor rExecutor = instantiateRExecutor(port);
			always_available.put(port, rExecutor);
			count++;
		}

		logAvailability();
	}

	public static Executor getObject() {
		int port;
		Executor rExecutor = null;
		logger.info("Inside R getObject");
		while (true) {
			synchronized (available_port) {
				if (available_port.size() != 0) {

					port = available_port.stream().findFirst().get();
					available_port.remove(port);
					synchronized (inUse_port) {
						inUse_port.add(port);
						logAvailability();
						rExecutor = always_available.get(port);
						if (rExecutor == null)
							rExecutor = instantiateRExecutor(port);
						return rExecutor;
					}

				} else {
					try {
						// TODO : review
						logger.info("No ports available..sleeping for some time");
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {
						logger.error("Error occurred while fetching getRExecutor");
						logger.error("No RServe port available");
						logger.error(e.getStackTrace());
					}
					continue;
				}
			}
		}

	}

	private static void logAvailability() {
		logger.info("Available R ports : " + available_port.toString());
		logger.info("InUse R ports : " + inUse_port.toString());
	}

	private static Executor instantiateRExecutor(int port) {
		Executor rObject = new RExecutor(port);
		return rObject;
	}

	/*
	 * Method kills the RServe process for the given port
	 */
	public static void releaseObject(Executor executor) {
		int port = executor.getPort();
		logger.info("Killing the RServe/Rconnection instance with port: " + executor.getPort());
		if (always_available.get(port) != null) {
			executor.endClientSession();
		} else {
			executor.endServerSession();
		}
		synchronized (inUse_port) {
			inUse_port.remove(port);
		}
		synchronized (available_port) {
			available_port.add(port);
		}
		logAvailability();
	}

}
