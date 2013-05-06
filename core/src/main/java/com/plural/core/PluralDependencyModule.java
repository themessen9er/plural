package com.plural.core;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.plural.core.persistence.DAOConcept;
import com.plural.core.persistence.DAOConceptLink;
import com.plural.core.persistence.DAORepository;
import com.plural.core.persistence.DAOResource;
import com.plural.core.persistence.jpa.DAOConceptImpl;
import com.plural.core.persistence.jpa.DAOConceptLinkImpl;
import com.plural.core.persistence.jpa.DAORepositoryImpl;
import com.plural.core.persistence.jpa.DAOResourceImpl;
import com.plural.core.services.ConceptService;
import com.plural.core.services.ConceptServiceImpl;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The plural GUICE dependency module
 */
public class PluralDependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("PluralPersistenceUnit"));
		
		bind(DAOConcept.class).to(DAOConceptImpl.class);
		bind(DAOConceptLink.class).to(DAOConceptLinkImpl.class);
		bind(DAORepository.class).to(DAORepositoryImpl.class);
		bind(DAOResource.class).to(DAOResourceImpl.class);
		
		bind(ConceptService.class).to(ConceptServiceImpl.class);
	}
	
	/**
	 * @author Francisco Vargas (themessen9er@gmail.com)
	 * 
	 * An initializer class for any types bound in {@link PluralDependencyModule}
	 * To initialize, simply do injector.getInstance(Initialize.class);
	 */
	public static class Initialize {
		
		/**
		 * The class constructor. Where all initialization logic should be contained.
		 */
		@Inject
		public Initialize(
				PersistService persistService) {
			persistService.start();
		}
	}

}
