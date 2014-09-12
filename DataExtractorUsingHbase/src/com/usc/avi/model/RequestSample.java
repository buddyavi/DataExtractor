package com.usc.avi.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This will be used for holding request Xml data *
 * 
 */
@XmlRootElement(name = "request")
public class RequestSample {

	private String id;

	private Variables var;

	@XmlAttribute(name = "student_id")
	public String getId() {
		return id;
	}

	@XmlElement(name = "variables")
	public Variables getVar() {
		return var;
	}

	public void setVar(Variables var) {
		this.var = var;
	}

}
