/**
 * Class :PyPooledObject
 * Purpose : Pooling Mechanism
 * 
 * 
 */

package com.bny.analytics.mlengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.bny.analytics.mlengine.utils.Constants;

public class PyPooledObject {

	final static Logger logger = Logger.getLogger(PyPooledObject.class);

	// As there will be a single connection per port, initializing Set rather
	// than list
	private static Set<Integer> available_port = Collections.synchronizedSet(new HashSet<Integer>());
	private static Set<Integer> inUse_port = Collections.synchronizedSet(new HashSet<Integer>());

	private static Map<Integer, Executor> always_available = new HashMap<Integer, Executor>();

	/*
	 * Initialize the available pool
	 */
	protected static void initializePool() {
		logger.info("Initializing available Python Pool");
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		int start_port = Integer.valueOf(rb.getString(Constants.PYTHON_START_PORT));
		int end_port = Integer.valueOf(rb.getString(Constants.PYTHON_END_PORT));
		for (int count = start_port; count <= end_port; count++) {
			available_port.add(count);
		}
		logger.info("Initialized the complete available pool from port " + start_port + "to " + end_port);
		logger.info("Total number of ports available are: " + available_port.size());

		initializeAlwaysOpenPool();
	}

	private static void initializeAlwaysOpenPool() {
		logger.info("Initializing always available python Pool");
		ResourceBundle rb = ApplicationContextListener.getResourceBundle();
		int always_open = Integer.valueOf(rb.getString(Constants.PYTHON_ALWAYS_OPEN));

		int count = 0;
		Iterator<Integer> iter = available_port.iterator();
		while (count < always_open && iter.hasNext()) {
			int port = iter.next();
			logger.info("Instantiating always open Python pool , count is : "+ count);
			Executor pyExecutor = instantiatePyExecutor(port);
			always_available.put(port, pyExecutor);
			count++;
		}
		logAvailability();
	}

	/*
	 * Destroying the available RServe pool
	 */
	protected static void destroyPool() {
		logger.info("Destroying available Python Pool");
		
		
		for (Entry<Integer, Executor> entry : always_available.entrySet()) {
			Executor executor = entry.getValue();
			executor.kill();
		}
		logger.info("Destroyed Python always open pool completed");
	}

	/*
	 * Returns the available PyExecutor object. Possible flows within the
	 * function: 1. if there are ports available : gets the first available
	 * port.removes that that specified port.Add it in the used list.
	 * instantiates PyExecutor object.Adds it in the inUse Map 2.else : sleep
	 * for 100 ms and continue processing the infinite loop
	 * 
	 */
	public static Executor getObject() {
		int port;
		Executor pyExecutor = null;
		while (true) {
			synchronized (available_port) {

				if (available_port.size() != 0) {

					port = available_port.stream().findFirst().get();
					available_port.remove(port);
					synchronized (inUse_port) {
						inUse_port.add(port);
						logger.info("Inside getObject");
						logAvailability();
						pyExecutor = always_available.get(port);
						if (pyExecutor == null)
							pyExecutor = instantiatePyExecutor(port);
						return pyExecutor;

					}

				} else {
					try {
						// Code should be reviewed
						logger.info("No ports available..sleeping for some time");
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {
						logger.error("Error occurred while fetching getPyExecutor");
						logger.error("No PyServe port available");
						logger.error(e.getStackTrace());
					}
					continue;
				}
			}
		}
	}
	
	
	private static void logAvailability() {
		logger.info("Available python ports : " + available_port.toString());
		logger.info("InUse python ports : " + inUse_port.toString());
	}

	/*
	 * Method instantiates PyExecutor instance for the given port Populates the
	 * available Map as well
	 */
	private static PyExecutor instantiatePyExecutor(int port) {
		PyExecutor pyObject = new PyExecutor(port);
		return pyObject;
	}

	/*
	 * Method kills the PyServe process for the given port
	 */
	public static void releaseObject(Executor pyObject) {
		int port = pyObject.getPort();
		logger.info("Killing the pyserve instance with port: " + pyObject.getPort());
		if (always_available.get(port) != null) {
			//Fix done , since python memory not being cleared. So technically closing and creating a connection every time
			//pyObject.endClientSession();
			pyObject.endServerSession();
			always_available.put(port, new PyExecutor(port));
		} else {
			pyObject.endServerSession();
		}
		synchronized (inUse_port) {
			inUse_port.remove(port);
		}
		synchronized (available_port) {
			available_port.add(port);
		}
		logger.info("Releasing Object");
		logAvailability();
	}

	private PyPooledObject() {
		// TODO Auto-generated constructor stub
	}

}
