package com.usc.avi.database.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;

/**
 * Interface defining the Database access methods
 */
public interface ExtractorDAO {

	/**
	 * Method used to execute the hbase get request and returns the list of
	 * keyvalue object back.
	 * 
	 */
	public List<KeyValue> getKeyValueList(String studentId,
			ArrayList<String> aReqColumns, String property, String property2)
			throws IOException;

	/**
	 * Method used to check Hbase connection status .
	 * 
	 */
	public boolean checkHbaseStatus() throws ZooKeeperConnectionException,
			MasterNotRunningException;

	/**
	 * Method used to close Hbase connection
	 * 
	 */
	public void closeConnection() throws IOException;

}
