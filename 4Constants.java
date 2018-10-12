package com.bny.analytics.mlengine.utils;

/**
 * Constants used across MlEngine application.
 * 
 * @author XBBNCDK
 *
 */
public interface Constants {

	public static final String HOSTNAME = "hostname";

	// Python specific
	public static final String PYTHONSOCKSERVE_LOCATION = "pythonSockServeLocation";
	public static final String PYTHON = "python";
	public static final String SPACE = " ";
	public static final String PYTHON_START_PORT = "python_start_port";
	public static final String PYTHON_END_PORT = "python_end_port";
	public static final String PYTHON_ALWAYS_OPEN = "python_always_open";

	// R specific
	public static final String REXE_LOCATION = "rExeLocation";
	public static final String BATCH_CMD = "batchCmd";
	public static final String RSERVE_LOCATION = "rServeLocation";
	public static final String RSERVE_OUTPUT_LOCATION = "rSeveOutputLocation";
	public static final String R_START_PORT = "r_start_port";
	public static final String R_END_PORT = "r_end_port";
	public static final String R_ALWAYS_OPEN = "r_always_open";

}
