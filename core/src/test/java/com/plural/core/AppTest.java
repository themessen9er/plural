package com.plural.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.plural.core.persistence.Concept;
import com.plural.core.persistence.DAOConcept;
import com.plural.core.persistence.DAOConceptLink;
import com.plural.core.persistence.TransactionWrapper;
import com.plural.core.persistence.jpa.ConceptEntity;
import com.plural.core.persistence.jpa.ConceptLinkEntity;
import com.plural.core.services.ConceptService;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws Exception 
     */
    public void testApp() throws Exception
    {
    	Injector injector = Guice.createInjector(new PluralDependencyModule());
    	injector.getInstance(PluralDependencyModule.Initialize.class);
    	
    	final DAOConcept daoConcept = injector.getInstance(DAOConcept.class);
    	final DAOConceptLink daoConceptLink = injector.getInstance(DAOConceptLink.class);

    	new TransactionWrapper<Void>(daoConcept) {

			@Override
			public Void executeTransactionCodeBlock() throws Exception {
		    	Concept a = daoConcept.create(new ConceptEntity("external systems", ""));
		    	Concept b = daoConcept.create(new ConceptEntity("hada", ""));
		    	
		    	daoConceptLink.create(new ConceptLinkEntity((ConceptEntity) a, (ConceptEntity) b));
		    	
		    	return null;
			}
		}.execute();
		
		
		ConceptService service = injector.getInstance(ConceptService.class);
		
		service.linkConcepts("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
		service.linkConcepts("a", "b", "c", "d", "e", "f");
		service.linkConcepts("four", "e", "d");
		
		System.out.println(service.getRelatedConcepts("four"));
		System.out.println(service.getRelatedConcepts("three"));
    }

    
}
