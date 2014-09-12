package com.usc.avi.model;

/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CellSetModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RowModel> Row;
	private String metadata;

	/**
	 * Constructor
	 */
	public CellSetModel() {
		this.Row = new ArrayList<RowModel>();
	}

	/**
	 * @param rows
	 *            the rows
	 */
	public CellSetModel(List<RowModel> Row) {
		super();
		this.Row = Row;
	}

	/**
	 * Add a row to this cell set
	 * 
	 * @param row
	 *            the row
	 */
	public void addRow(RowModel row) {
		Row.add(row);
	}

	@XmlElement(name = "row")
	public List<RowModel> getRow() {
		return Row;
	}

	public void setRow(List<RowModel> row) {
		Row = row;
	}

	@XmlElement(name = "metaData")
	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	
}
