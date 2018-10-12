package com.bny.analytics.mlengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ObjectPool {

	final static Logger logger = Logger.getLogger(ObjectPool.class);
	private static int maxConnections = 1000;
	private static List<PooledObject> available = new ArrayList<PooledObject>();
	private static List<PooledObject> inUse = new ArrayList<PooledObject>();
	
	public static int getAvailablePoolSize()
	{
		return available.size();
	}
	
	public static int getInIsePoolSize()
	{
		return inUse.size();
	}
	
	/*
	 * This will initialize default 
	 */
	public static void InitializeDefaultPool()
	{
		
	}
	
	public static void getObject()
	{
		
	}
	
	private static int getOpenPort()
	{
		return 0;
	}
	
	private static boolean isPortAvailable(int port)
	{
		return false;
	}
	
}
