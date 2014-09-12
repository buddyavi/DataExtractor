package com.usc.avi.memcached;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.usc.avi.constants.Constants;
import com.usc.avi.util.ApachePropertyWrapper;

/**
 * CLass for establishing MySql connection
 * 
 */

public class MySqlConnection {

	private static Logger logger = LoggerFactory
			.getLogger(MySqlConnection.class);

	/*private static Properties prop;
	static String propFile = Constants.PROPERTY_FILE;
	static {
		prop = LendawarePropertiesLoader.loadJobProps(propFile);
	}*/

	static String url = "jdbc:mysql://"
			+ ApachePropertyWrapper.getProperty(Constants.MYSQL_SERVER_NAME) + "/"
			+ ApachePropertyWrapper.getProperty(Constants.MYSQL_DATABASE_NAME);

		public static Connection connection = null;

	/**
	 * Method to establish connection to mysql DB
	 * 
	 * @return connection- mysql connection object
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException, Exception {
		logger.info("Establishing connection at: " + url);

		Class.forName(ApachePropertyWrapper.getProperty(Constants.MYSQL_DRIVER_NAME));
		connection = DriverManager.getConnection(url,
				ApachePropertyWrapper.getProperty(Constants.MYSQL_USER),
				ApachePropertyWrapper.getProperty(Constants.MYSQL_PASSWORD));

		return connection;

	}

}
