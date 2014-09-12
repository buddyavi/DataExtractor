package com.usc.avi.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an init class which will be used to load the project property file
 * 
 */
public class Init {

	protected static Logger logger = LoggerFactory.getLogger(Init.class);
	
	public static PropertiesConfiguration config = new PropertiesConfiguration();

	/**
	 * This is an initialization Object. All initialization will happen in this
	 * class.
	 */
	/**
	 * Loading properties file.
	 */
	public static void loadPropFile(String strFileName) {
		logger.info("Loading Properties file: " + strFileName);

		try {
			// Loading the properties file
			config.load(Init.class.getClassLoader().getResourceAsStream(strFileName));
			logger.info("successfully loaded property file " + strFileName);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
