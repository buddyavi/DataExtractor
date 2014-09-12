package com.usc.avi.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This will be used for holding variables in request xml
 */
@XmlRootElement(name = "variables")
public class Variables {

	private List<VariableModel> variable = new ArrayList<VariableModel>();

	public void addCell(VariableModel cell) {
		variable.add(cell);
	}

	@XmlElement(name = "variable")
	public List<VariableModel> getVariable() {
		return variable;
	}

}
