package com.usc.avi.constants;

public class Constants {

	// Project property file
	public final static String PROPERTY_FILE = "extractor.properties";

	// Database tables and column families
	public final static String STUDENT_TABLE_NAME = "student_table_name";
	public final static String STUDENT_TABLE_CF = "student_table_cf";
	
	// Zookeeper Quorums and client Port
	public final static String QUORUMS = "zookeeper_quorums";
	public final static String CLIENT_PORT = "client_port";

	
	// Data types supported
	public static final String FLOAT_DATA_TYPE = "float";
	public static final String STRING_DATA_TYPE = "string";
	public static final String DATE_DATA_TYPE = "date";
	public static final String INT_DATA_TYPE = "integer";
	public static final String DOUBLE_DATA_TYPE = "double";

	// Date format
	public static final String DATE_FORMAT = "date_format";

	// Memcache server constants	
	public static final String MEMCACHE_SERVERS ="memcache_servers";
	public static final String MEMCACHE_TIME_TO_LIVE="memcache_time_to_live";
	
	
	// MySql connection constants
	public static final String MYSQL_DRIVER_NAME = "mysql_driver_name";
	public static final String MYSQL_SERVER_NAME = "mysql_server_name";
	public static final String MYSQL_DATABASE_NAME = "mysql_database_name";
	public static final String MYSQL_USER = "mysql_user";
	public static final String MYSQL_PASSWORD = "mysql_password";

	// MySql query used to populate memcache server
	public static final String QUERY_TO_GET_MAPPING = "select NAME,DATA_TYPE from variable";


	//Project properties
	public static final String SEPERATOR=",";

	
}
