package com.usc.avi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representation of a row. A row is a related set of cells, grouped by common
 * row key. RowModels do not appear in results by themselves. They are always
 * encapsulated within CellSetModels.
 * 
 */
@XmlRootElement(name = "row")
public class RowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String studentId;
	
	private Columns col;

	// private List<CellModel> cells = new ArrayList<CellModel>();

	/**
	 * Default constructor
	 */
	public RowModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param key
	 *            the row key
	 */
	public RowModel(final String key) {
		this.studentId = key;
		// cells = new ArrayList<CellModel>();
	}

	/**
	 * Constructor
	 * 
	 * @param key1
	 *            the row cm key
	 * @param key2
	 *            the row mof key
	 */
	public RowModel(final String key1, final Columns col) {
		
		this.studentId = key1;
		this.col = col;
		// cells = new ArrayList<CellModel>();
	}

	
	/**
	 * @return the cm key
	 */
	@XmlAttribute(name = "student_id")
	public String getCm13() {
		return studentId;
	}

	public void setCm13(String id) {
		this.studentId = id;
	}

	/*
	 * public void addCell(CellModel cell) { cells.add(cell); }
	 * 
	 * @XmlElement(name = "Column") public List<CellModel> getCells() { return
	 * cells; }
	 */

	@XmlElement(name = "columns")
	public Columns getCol() {
		return col;
	}

	public void setCol(Columns col) {
		this.col = col;
	}
}