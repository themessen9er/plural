package com.bacon.mayo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bacon.core.BaconAnalysisUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MayoListener extends GuiceServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MayoListener.class);

	private Injector i;
	
	@Override
	public void contextInitialized(javax.servlet.ServletContextEvent servletContextEvent) {
		i = createInjector();

		BaconAnalysisUtil.analize(i);

		super.contextInitialized(servletContextEvent);
	};


	@Override
	protected Injector getInjector() {
		return i;
	}

	protected Injector createInjector() {
		Injector injector = Guice.createInjector(
				new MayoServletModule());
		return injector;
	}

}
