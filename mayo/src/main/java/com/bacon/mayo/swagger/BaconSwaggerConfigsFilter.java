package com.bacon.mayo.swagger;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaconSwaggerConfigsFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaconSwaggerConfigsFilter.class);
	private static final String SWAGGER_API_DOCS_URL = "swagger.api.docs.url";

	private String url = "";

	@Inject
	public BaconSwaggerConfigsFilter(@Named("bacon." + SWAGGER_API_DOCS_URL) String url) {
		this.url = url;
		LOGGER.debug("SwaggerConfigsFilter: " + this.url);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (HttpServletResponse.class.isAssignableFrom(response.getClass())) {
			((HttpServletResponse) response).addHeader(SWAGGER_API_DOCS_URL, url);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Do nothing
	}

}
