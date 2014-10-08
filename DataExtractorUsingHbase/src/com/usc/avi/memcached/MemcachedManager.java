package com.usc.avi.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.BulkFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HostAndPort;
import com.usc.avi.constants.Constants;
import com.usc.avi.util.ApachePropertyWrapper;

/**
 * Class file for connecting to memcache and getting Map of column to data type
 * values
 * 
 */
public class MemcachedManager {
	private static Logger logger = LoggerFactory
			.getLogger(MemcachedManager.class);

	

	/**
	 * Method to get variable to data type mapping from the MySql Db
	 * 
	 * @param variables
	 *            list of variable whose mapping we need
	 * @return varDataTypeMapping variable to data type mapping
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public Map<String, Object> getVarDataTypeMapping(List<String> variables)
			throws ExecutionException, InterruptedException, IOException {

		logger.info("Inside getVarDataTypeMapping() method: getting variable to datatype mapping from memcache");
	    ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
        cfb.setFailureMode(FailureMode.Redistribute);
        cfb.setDaemon(true);
        cfb.setProtocol(Protocol.TEXT);
        cfb.setLocatorType(Locator.ARRAY_MOD);
        cfb.setShouldOptimize(Boolean.FALSE);
		// Establishing connection to memcache server
		        
        MemcachedClient memcachedClient = new MemcachedClient(cfb.build(),
				getActiveServers());
        
		Map<String, Object> varDataTypeMapping = null;		
		// Asynchronously get a bunch of objects from the cache.
		BulkFuture<Map<String, Object>> f = memcachedClient
				.asyncGetBulk(variables);
		
		try {
			
			// Try to get a value, for up to 5 seconds, and cancel if it doesn't
			// return			
			varDataTypeMapping = f.get(5, TimeUnit.SECONDS);
			
		} catch (TimeoutException te) {
			// Since we don't need this, go ahead and cancel the operation.
			f.cancel(false);
			// TODO
			// If MemCached is unavailable then what
		} finally {
			memcachedClient.shutdown(1, TimeUnit.SECONDS);
		}
		
		return varDataTypeMapping;
	}
	
	
	private ArrayList<InetSocketAddress> getActiveServers() {
		
		ArrayList<InetSocketAddress> addrs = new ArrayList<InetSocketAddress>();

		try {

			String serverList = ApachePropertyWrapper
					.getProperty(Constants.MEMCACHE_SERVERS);
			String memServers[] = serverList.split(Constants.SEPERATOR);
            
            
			for (String server : memServers) {
				HostAndPort hostPort = HostAndPort.fromString(server);
				
				// Attempt to connect
				Socket sc = null;
				try {
					sc = new Socket(hostPort.getHostText(), hostPort.getPort());
					logger.info("hostname connected " + hostPort.getHostText());
					addrs.add(new InetSocketAddress(hostPort.getHostText(), hostPort.getPort()));
					break;
				} catch (Exception e) {
					// Do nothing
				} finally {
					if(null != sc) {
						sc.close();
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception occurs while getting active servers" + e);
		}

		return addrs;
	}

}
