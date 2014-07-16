package com.bacon.core;

import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ConfigModule extends AbstractModule {

	public static final String PROPERTIES_PREFIX = "bacon."; 

	private final PropertiesConfiguration properties;


	public ConfigModule(PropertiesConfiguration props) {
		this.properties = props;
	}

	@Override
	protected void configure() {
		Iterator<String> keyIterator = properties.getKeys();
		while(keyIterator.hasNext()) {
			String key = keyIterator.next();
			bindConstant().annotatedWith(Names.named(PROPERTIES_PREFIX + key)).to(properties.getString(key));
		}
		bind(PropertiesConfiguration.class).toInstance(properties);
	}

}
