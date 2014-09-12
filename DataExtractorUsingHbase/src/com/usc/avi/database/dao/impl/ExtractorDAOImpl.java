package com.usc.avi.database.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.DoNotRetryIOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.usc.avi.constants.Constants;
import com.usc.avi.database.dao.ExtractorDAO;
import com.usc.avi.util.ApachePropertyWrapper;

/*
 * Class implementing the Hbase Database access methods
 */

public class ExtractorDAOImpl implements ExtractorDAO {

	private static Logger log = LoggerFactory.getLogger(ExtractorDAOImpl.class);
	private static Configuration conf = null;
	private final static int MAX = 10;
	private static HTablePool pool;
	//private static Properties prop;
	//private static String propFile = Constants.PROPERTY_FILE;
	private HBaseAdmin hadmin = null;
	/**
	 * static block for initializing Hbase connection
	 */
	static {
		// loading properties file
		//prop = LendawarePropertiesLoader.loadJobProps(propFile);
		conf = HBaseConfiguration.create();

		// setting configuration properties

		/* Comma separated list of servers in the ZooKeeper ensemble */
		conf.set("hbase.zookeeper.quorum", ApachePropertyWrapper.getProperty(Constants.QUORUMS));

		/* The port at which the clients will connect */
		conf.set("hbase.zookeeper.property.clientPort",
				ApachePropertyWrapper.getProperty(Constants.CLIENT_PORT));
		/*
		 * Used as maximum for all retryable operations such as the getting of a
		 * cell's value, starting a row update, etc
		 */
		conf.set("hbase.client.retries.number", "1");

		/* RecoverableZookeeper retry count */
		conf.set("zookeeper.recovery.retry", "1");

		/* ZooKeeper session timeout in milliseconds */
		conf.set("zookeeper.session.timeout", "3000");

		/* how long HBase client applications take for a remote call to time out */
		conf.set("hbase.rpc.timeout", "2000");

		// initialising HBase table pool
		pool = new HTablePool(conf, MAX);

	}

	/**
	 * 
	 * This method executes the hbase get request and returns the list of
	 * keyvalue object back.
	 * 
	 * @param sRowKey
	 *            hbase row key
	 * @param lstColumns
	 *            list of required columns
	 * @param sTableName
	 *            Hbase table name
	 * @param sColFamily
	 *            Hbase column family
	 * @return lstKeyValue list of HBase keyvalue pairs
	 * @throws IOException
	 */
	public List<KeyValue> getKeyValueList(String sRowKey,
			ArrayList<String> lstColumns, String sTableName, String sColFamily)
			throws IOException, ZooKeeperConnectionException,
			MasterNotRunningException {
		log.info("Inside getKeyValueList method.");

		HTableInterface hTableObj = pool.getTable(sTableName);

		List<KeyValue> lstKeyValue = null;

		try {
			// preparing the get object
			Get get = new Get(sRowKey.getBytes());
			for (String s : lstColumns) {
				get.addColumn(Bytes.toBytes(sColFamily), Bytes.toBytes(s));
			}

			Result result = hTableObj.get(get);
			log.info("Result set size: " + result.size());
			if (result != null && result.size() > 0) {
				lstKeyValue = result.list();

			}
		} catch (DoNotRetryIOException e) {
			log.warn(StringUtils.stringifyException(e));
		} finally {
			hTableObj.close();
		}

		return lstKeyValue;
	}

	/**
	 * This method is used to check Hbase status
	 */
	public boolean checkHbaseStatus() {
		log.info("Checking Hbase Status");

		boolean bFlag = true;

		try {
			/*
			 * HBaseAdmin Object initialisation throws Exception if HBase
			 * servers are not reachable
			 */
			hadmin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			log.error("Error:" + e);
			bFlag = false;
		} catch (ZooKeeperConnectionException e) {
			log.error("Error:" + e);
			bFlag = false;
		}
		return bFlag;
	}

	/**
	 * This method closes the Hbase session established
	 */

	public void closeConnection() throws IOException {
		log.info("Closing Hbase session");

		if (hadmin != null) {
			hadmin.close();
		}

	}

}
