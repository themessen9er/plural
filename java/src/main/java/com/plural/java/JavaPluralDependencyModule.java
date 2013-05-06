package com.plural.java;

import com.google.inject.AbstractModule;
import com.plural.core.PluralDependencyModule;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The dependency module for the Plural Java extensions
 */
public class JavaPluralDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new PluralDependencyModule());
		
		bind(TagProcessor.class).to(TagProcessorImpl.class);
	}

}
