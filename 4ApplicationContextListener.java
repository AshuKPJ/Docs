package com.bny.analytics.mlengine;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

	final static Logger logger = Logger.getLogger(ApplicationContextListener.class);
	private static ResourceBundle config_resourceBundle;
	
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("Starting up ApplicationContextListener!");
		initializeLogging(servletContextEvent.getServletContext());
		initializeResourceBundle();
		PyPooledObject.initializePool();
		RPooledObject.initializePool();
	
	}

	/*
	 * Method to initialize logging within web application
	 */
	private void initializeLogging(ServletContext context) {
		logger.info("Initializing log configuration");
		String log4jConfigFile = context.getInitParameter("log4j-config-location");
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		PropertyConfigurator.configure(fullPath);
	}
	
	/*
	 * Method reads config.properties file and loads it into memory
	 * TODO : we might want to instantiate using Spring IOC
	 */
	private void initializeResourceBundle(){
		logger.info("Loading config.properties file");
		config_resourceBundle=ResourceBundle.getBundle("com.bny.analytics.mlengine.config");
	}

	/*
	 * This getter method should be used for fetching the resource bundle
	 */
	public static ResourceBundle getResourceBundle()
	{
		return config_resourceBundle;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		PyPooledObject.destroyPool();
		RPooledObject.destroyPool();
		logger.info("Shutting down ApplicationContextListener!");
		
	}
}
