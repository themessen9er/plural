package com.bacon.mayo.swagger;

import java.util.HashMap;

import javax.inject.Singleton;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.model.ApiInfo;

public class SwaggerModule extends ServletModule {

	@Override
	protected void configureServlets() {

		bind(BaconSwaggerConfigsFilter.class).in(Singleton.class);
		filter("/swagger", "/swagger/*").through(BaconSwaggerConfigsFilter.class);

		serve("/api-docs",  "/api-docs/*").with(new BaconApiDeclarationServlet());

		HashMap<String, String> servletConfig = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put(PackagesResourceConfig.PROPERTY_PACKAGES, "com.wordnik.swagger.jaxrs.listing");
				put("swagger.filter", "com.bacon.mayo.swagger.BaconSwaggerFilter");
				put("swagger.resource.package", "com.bacon");
			}
		};
		bind(BaconServerReaderConfig.class).in(Singleton.class);
		serve("").with(BaconServerReaderConfig.class, servletConfig);
	}

	public void init(Injector i) {

		final String basePath = i.getInstance(Key.get(String.class, Names.named("bacon.base_uri"))) + "/ws";

		SwaggerConfig swaggerConfig = ConfigFactory.config();
		swaggerConfig.setBasePath(basePath);
		swaggerConfig.setApiVersion("1.0.0");
		swaggerConfig.setApiInfo(new ApiInfo(
				"Bacon", 
				"This is the webservice documentation Bacon documentation", 
				null, 
				"fvargas@pminds.pt", 
				null, 
				null));
	}

}
