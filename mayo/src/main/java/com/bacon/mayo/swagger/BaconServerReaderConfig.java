package com.bacon.mayo.swagger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import com.wordnik.swagger.servlet.config.ServletScanner;

public class BaconServerReaderConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(BaconServerReaderConfig.class);
	

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		LOGGER.info(String.format("Configuring Swagger - %s", ConfigFactory.config()));
		
		ServletScanner scanner = new ServletScanner();
		ScannerFactory.setScanner(scanner);
		scanner.setResourcePackage(servletConfig.getInitParameter("swagger.resource.package"));
		ClassReaders.setReader(new DefaultJaxrsApiReader());
		ConfigFactory.setConfig(ConfigFactory.config());
	}

}
