package com.plural.core;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.plural.core.persistence.Concept;
import com.plural.core.persistence.DAOConcept;
import com.plural.core.persistence.DAOConceptLink;
import com.plural.core.persistence.TransactionWrapper;
import com.plural.core.persistence.jpa.ConceptEntity;
import com.plural.core.persistence.jpa.ConceptLinkEntity;
import com.plural.core.services.ConceptService;
import com.plural.core.util.Tag;

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
    @Tag({"three", "test"})
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
		
		Reflections reflections = new Reflections( 
			    new ConfigurationBuilder().setUrls( 
			    ClasspathHelper.forPackage( "com.plural.core" ) ).setScanners(
			    new MethodAnnotationsScanner() ) );
		
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Tag.class);
	    
		for(Class<?> c : classes) {
			Tag tag = c.getAnnotation(Tag.class);
			service.linkConcepts(tag.value());
		}
		
		
		Set<Method> methods = reflections.getMethodsAnnotatedWith(Tag.class);
    
		for(Method m : methods) {
			Tag tag = m.getAnnotation(Tag.class);
			service.linkConcepts(tag.value());
		}
		
		System.out.println(service.getRelatedConcepts("three"));
		
		
		 // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("/Users/Franciscovargascabrita/Documents/repositories/git/plural/core/src/test/java/com/plural/core/AppTest.java");

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
		
    }
    
    private static class MethodVisitor extends VoidVisitorAdapter {

    	
    	@Override
    	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
    		// TODO Auto-generated method stub
    		System.out.println(n.getName() + " - " + n.getBeginLine());
    	}
    	
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            System.out.println(n.getName() + " - " + n.getBeginLine());
        }
    }

    
}
