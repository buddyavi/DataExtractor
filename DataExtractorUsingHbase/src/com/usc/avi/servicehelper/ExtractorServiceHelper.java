package com.usc.avi.servicehelper;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.usc.avi.constants.Constants;
import com.usc.avi.constants.ErrorCode;
import com.usc.avi.database.dao.ExtractorDAO;
import com.usc.avi.database.dao.impl.ExtractorDAOImpl;
import com.usc.avi.exceptions.InvalidRequestException;
import com.usc.avi.exceptions.StudentNotFoundException;
import com.usc.avi.memcached.MemcachedManager;
import com.usc.avi.model.CellModel;
import com.usc.avi.model.CellSetModel;
import com.usc.avi.model.Columns;
import com.usc.avi.model.RequestSample;
import com.usc.avi.model.RowModel;
import com.usc.avi.model.VariableModel;
import com.usc.avi.util.ApachePropertyWrapper;

/**
 * Class holding the business logic for the service request and the helper
 * methods to serve the request
 * 
 */
public class ExtractorServiceHelper {
	private static Logger log = LoggerFactory
			.getLogger(ExtractorServiceHelper.class);

	private ExtractorDAO TableDao;

	

	/**
	 * Constructor for initializing the DAO object
	 */
	public ExtractorServiceHelper() {
		this.TableDao = new ExtractorDAOImpl();
	}

	/**
	 * Method to unmarshall the string Xml
	 * 
	 * @param requestXml
	 *            string
	 */

	public RequestSample unmarshalXml(String requestXml) throws JAXBException,
			SAXException, IOException, Exception {

		log.info("inside unmarshalXml method");

		RequestSample request = new RequestSample();

		JAXBContext jaxbContext = JAXBContext.newInstance(RequestSample.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		request = (RequestSample) jaxbUnmarshaller.unmarshal(new StringReader(
				requestXml));

		return request;

	}

	/**
	 * Generates the response model
	 * 
	 * @param rqs
	 *            request model
	 * @throws IOException
	 */
	public CellSetModel generateModel(String requestXml) throws IOException {
		log.info("inside generateModel() method");
		// init variables
		CellSetModel model = new CellSetModel();

		long lTimeDiff = 0l;
		ErrorCode errCode = null;

		RequestSample rqs = new RequestSample();
		long lStart = System.currentTimeMillis();

		try {

			// Checking Hbase availability
			boolean status = this.TableDao.checkHbaseStatus();
			log.debug("Connection Status: " + status);
			if (!status) {
				throw new ZooKeeperConnectionException(
						"Unable to connection to HBase.");
			}

			// trimming the request xml
			requestXml = requestXml.trim();

			// Unmarshalling xml to Request object
			rqs = unmarshalXml(requestXml);

			// init variables
			MemcachedManager memCachedManager = new MemcachedManager();
			ArrayList<String> aReqColumns = new ArrayList<String>();

			List<KeyValue> lstColumns = new ArrayList<KeyValue>();
			List<KeyValue> lstTemp = null;

			// populating values received from request XML
			String studentId = rqs.getId().trim();
			// checking if student Id is blank
			if (studentId == null || studentId.isEmpty()) {
				throw new InvalidRequestException("student Id is blank");
			}

			// populating the columns in request
			List<VariableModel> lVariables = rqs.getVar().getVariable();
			for (VariableModel objVariable : lVariables) {
				aReqColumns.add(objVariable.getName().trim());
			}

			/**
			 * Process Student data request
			 */
			lstTemp = this.TableDao.getKeyValueList(studentId, aReqColumns,
					ApachePropertyWrapper
							.getProperty(Constants.STUDENT_TABLE_NAME),
					ApachePropertyWrapper
							.getProperty(Constants.STUDENT_TABLE_CF));

			if (lstTemp == null) {
				throw new StudentNotFoundException(
						"Passed Student ID does not have an match in database.");
			} else {
				lstColumns.addAll(lstTemp);

			}

			// adding the columns to rows
			model.addRow(procKeyValRow(lstColumns, studentId,
					memCachedManager.getVarDataTypeMapping(aReqColumns)));

			// Reach here in success state
			errCode = ErrorCode.SUCCESS;

		} catch (JAXBException jax) {
			log.error("JAXBException occured:" + jax);
			errCode = ErrorCode.INVALID_REQUEST;

		} catch (SAXException sax) {
			log.error("SAXException occured:" + sax);
			errCode = ErrorCode.INVALID_REQUEST;

		} catch (ZooKeeperConnectionException zo) {
			log.error("Exception  occured:" + zo);
			errCode = ErrorCode.HBASE_CONNECTION_ERROR;

		} catch (MasterNotRunningException m) {
			log.error("Exception  occured:" + m);
			errCode = ErrorCode.HBASE_CONNECTION_ERROR;

		} catch (IOException io) {
			log.error("IOException  occured:" + io);
			errCode = ErrorCode.INVALID_REQUEST;

		} catch (InvalidRequestException inv) {

			log.error(inv.getMessage());
			errCode = ErrorCode.INVALID_REQUEST;

		} catch (StudentNotFoundException cm) {
			log.error("Exception :" + cm.getMessage());
			errCode = ErrorCode.STUDENT_MISSING;
		} catch (Exception e) {

			log.error("Exception :" + e);
			errCode = ErrorCode.APP_ERROR;
		} finally {
			// closing Hbase connection
			this.TableDao.closeConnection();
		}

		long lEnd = System.currentTimeMillis();
		lTimeDiff = lEnd - lStart;
		log.info("TT taken: " + lTimeDiff + " (Milli Seconds)");
		model.setMetadata(errCode.getRetMessage());
		// returning failure response for no records found return
		return model;

	}

	/**
	 * This method converts the keyvalue list of hbase row details into RowModel
	 * Object
	 * 
	 * @param lstKeyVal
	 * @param studentId
	 * @param sMofId
	 * @param varDataTypeMapping
	 * @return
	 */
	public RowModel procKeyValRow(List<KeyValue> lstKeyVal, String studentId,
			Map<String, Object> varDataTypeMapping) throws Exception {
		log.info("Inside procKeyValRow values");
		RowModel rowModel = null;
		Columns objColumns = new Columns();

		for (KeyValue keyValue : lstKeyVal) {

			addCell(objColumns, keyValue, varDataTypeMapping);
		}

		// init the object
		rowModel = new RowModel(studentId, objColumns);
		return rowModel;

	}

	/**
	 * Method to convert the result in bytes to respective data types based on
	 * variable to data type mapping
	 * 
	 * @param col
	 * @param keyValue
	 * @param varDataTypeMapping
	 */
	public void addCell(Columns col, KeyValue keyValue,
			Map<String, Object> varDataTypeMapping) {
		log.info("Inside addcell() method : getting variable to data type mapping");
		String variable = Bytes.toString(keyValue.getQualifier());
		String dataType = null;
		log.info("Variable Name:" + variable);
		Object value = null;

		if (varDataTypeMapping != null) {

			dataType = (String) varDataTypeMapping.get(variable);
			log.info("Variable : " + variable + ", dataType : " + dataType);

			// generic value validation

			byte[] bValue = keyValue.getValue();
			if (bValue.length > 0) {
				if (dataType != null) {

					if (dataType.equalsIgnoreCase(Constants.FLOAT_DATA_TYPE)) {
						value = new Float(Bytes.toFloat(bValue));
					} else if (dataType
							.equalsIgnoreCase(Constants.STRING_DATA_TYPE)) {
						value = Bytes.toString(bValue);
					} else if (dataType
							.equalsIgnoreCase(Constants.INT_DATA_TYPE)) {
						value = Bytes.toInt(keyValue.getValue());
					} else if (dataType
							.equalsIgnoreCase(Constants.DOUBLE_DATA_TYPE)) {
						// to convert the E-5 notation to acutal value
						// representation
						value = new BigDecimal(Double.toString(Bytes
								.toDouble(bValue)));
						// value = Bytes.toDouble(bValue);
					} else if (dataType
							.equalsIgnoreCase(Constants.DATE_DATA_TYPE)) {
						long l = Bytes.toLong(bValue);
						log.info("Date Long: " + l);
						Date d = new Date(l);
						SimpleDateFormat df = new SimpleDateFormat(
								ApachePropertyWrapper
										.getProperty(Constants.DATE_FORMAT));

						value = df.format(d);
					}
				}
			} else {
				value = Bytes.toString("".getBytes());
			}

			if (value == null) {

				log.info("Value is null");
			}

		}
		if (dataType == null) {
			log.info("Variable Name: " + variable
					+ " Entry missing in memcache!!!");
		} else {
			col.addCell(new CellModel(variable, keyValue.getTimestamp(), value
					.toString()));
		}
	}

}
