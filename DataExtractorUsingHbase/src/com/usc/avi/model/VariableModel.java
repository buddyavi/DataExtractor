package com.usc.avi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * This will hold individual elements of the request Xml
 * 
 */
@XmlRootElement(name = "variable")
public class VariableModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;

	@XmlValue
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
