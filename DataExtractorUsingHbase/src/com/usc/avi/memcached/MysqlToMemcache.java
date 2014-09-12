package com.usc.avi.memcached;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.usc.avi.constants.Constants;

/**
 * Class for getting column to data type mapping from mySql db
 * 
 */
public class MysqlToMemcache {
	private static Logger logger = LoggerFactory
			.getLogger(MysqlToMemcache.class);
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Statement stmt = null;

	/**
	 * Method to get column to data type mapping from mysql DB
	 * 
	 * @return map
	 */
	public Map<String, String> getColDatatype() throws SQLException, Exception {
		logger.info("inside getColDatatype() method");
		connection = MySqlConnection.getConnection();
		Map<String, String> dataMap = new HashMap<String, String>();

		stmt = connection.createStatement();
		rs = stmt.executeQuery(Constants.QUERY_TO_GET_MAPPING);
		while (rs.next()) {

			String column1 = rs.getString("NAME").trim();
			String column2 = rs.getString("DATA_TYPE").trim();
			// populating the data map
			dataMap.put(column1, column2);
		}

		logger.info("Column to data type map size: " + dataMap.size());
		return dataMap;

	}

}
