package com.usc.avi.service;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.usc.avi.constants.Constants;
import com.usc.avi.model.CellSetModel;
import com.usc.avi.servicehelper.ExtractorServiceHelper;
import com.usc.avi.util.Init;

/*
 * Root rest service class . 
 */
@Controller
@RequestMapping("/")
public class ExtractorService {

	private static Logger log = LoggerFactory.getLogger(ExtractorService.class);

	/**
	 * Main request service method that will be called for interpreting the
	 * request xml and building the response
	 * 
	 * @param requestXml
	 * @return
	 * @throws JAXBException
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/xml" ,consumes="application/xml")
	public ResponseEntity<CellSetModel> requestService(
			@RequestBody String requestXml) throws JAXBException, Exception {

		log.info("Inside service method");
		// loading properties file
		Init.loadPropFile(Constants.PROPERTY_FILE);

		ExtractorServiceHelper serviceHelper = new ExtractorServiceHelper();
        log.info("\nRequest xml:\n"+requestXml);
		CellSetModel model = serviceHelper.generateModel(requestXml);

		return new ResponseEntity<CellSetModel>(model, HttpStatus.OK);

	}

	
}
