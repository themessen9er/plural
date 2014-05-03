package com.bacon.mayo;

import com.bacon.core.CoreBaconModule;
import com.google.inject.AbstractModule;

public class MayoModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CoreBaconModule());
	}

}
