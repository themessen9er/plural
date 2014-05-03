package com.bacon.java;

import com.bacon.core.CoreBaconModule;
import com.google.inject.AbstractModule;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The dependency module for the Plural Java extensions
 */
public class JavaBaconModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CoreBaconModule());
		bind(JavaBaconAnalysisProvider.class);
	}

}
