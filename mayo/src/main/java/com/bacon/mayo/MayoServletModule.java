package com.bacon.mayo;

import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class MayoServletModule extends JerseyServletModule {

	public final static Map<String,String> servletConfig = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("com.sun.jersey.config.property.packages",
					"com.bacon.mayo.ws");
			put("com.sun.jersey.api.json.POJOMappingFeature", "true");
			put("com.sun.jersey.spi.container.ContainerRequestFilters",
					"com.sun.jersey.api.container.filter.LoggingFilter");
			put("com.sun.jersey.spi.container.ContainerResponseFilters",
					"com.sun.jersey.api.container.filter.LoggingFilter");
		}
	};

	public MayoServletModule(){
		super();
	}

	@Override
	protected void configureServlets() {
		install(new MayoModule());

		serve("/ws/*").with(GuiceContainer.class, servletConfig);

		return;
	}

}
