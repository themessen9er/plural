package com.bacon.core;

import com.bacon.core.services.ConceptService;
import com.bacon.core.services.ConceptServiceImpl;
import com.google.inject.AbstractModule;


/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The plural GUICE dependency module
 */
public class CoreBaconModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConceptService.class).to(ConceptServiceImpl.class).asEagerSingleton();;
	}

}
