package com.bacon.mayo;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.bacon.core.ConfigModule;
import com.bacon.core.CoreBaconModule;
import com.bacon.mayo.swagger.SwaggerModule;
import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;

public class MayoModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CoreBaconModule());
		install(new SwaggerModule());
		try {
			install(new ConfigModule(new PropertiesConfiguration("config.properties")));
		} catch (ConfigurationException e) {
			Throwables.propagate(e);
		}
	}

}
