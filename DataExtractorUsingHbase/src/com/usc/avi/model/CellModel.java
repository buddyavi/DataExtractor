package com.usc.avi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.hadoop.hbase.HConstants;

/**
 * Representation of a cell. A cell is a single value associated a column and
 * optional qualifier, and either the timestamp when it was stored or the user-
 * provided timestamp if one was explicitly supplied.
 * 
 */
@XmlRootElement(name = "Column")
public class CellModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private long timestamp = HConstants.LATEST_TIMESTAMP;
	private String name;
	private String value;

	/**
	 * Default constructor
	 */
	public CellModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param column
	 * @param qualifier
	 * @param timestamp
	 * @param value
	 */
	public CellModel(String qualifier, long timestamp, String value) {
		this.name = qualifier;
		this.timestamp = timestamp;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	@XmlAttribute
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the timestamp
	 */
	@XmlAttribute
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return true if the timestamp property has been specified by the user
	 */
	public boolean hasUserTimestamp() {
		return timestamp != HConstants.LATEST_TIMESTAMP;
	}

}
