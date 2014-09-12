package com.usc.avi.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for holding Columns in response xml
 * 
 * 
 */
@XmlRootElement(name = "columns")
public class Columns {

	private List<CellModel> column = new ArrayList<CellModel>();

	public void addCell(CellModel cell) {
		column.add(cell);
	}

	@XmlElement(name = "column")
	public List<CellModel> getColumn() {
		return column;
	}

}
