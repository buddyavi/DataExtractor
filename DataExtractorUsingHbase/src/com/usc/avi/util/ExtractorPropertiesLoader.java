package com.usc.avi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractorPropertiesLoader {

	public static Properties hbaseRestProps = new Properties();
	private static Logger logger = LoggerFactory
			.getLogger(ExtractorPropertiesLoader.class);

	/**
	 * Constructor
	 */
	public ExtractorPropertiesLoader() {
	}

	/**
	 * Method: loadJobProps
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadJobProps(String strFileName) {

		logger.info("Loading Properties file: " + strFileName);

		try {

			// Loading the properties file

			hbaseRestProps.load(ExtractorPropertiesLoader.class
					.getClassLoader().getResourceAsStream(strFileName));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		// return
		return hbaseRestProps;
	}

	public static Properties getHbaseRestProps() {
		return hbaseRestProps;
	}

	public static void setHbaseRestProps(Properties hbaseRestProps) {
		ExtractorPropertiesLoader.hbaseRestProps = hbaseRestProps;
	}

}
