package com.bacon.java;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

import com.bacon.core.services.BaconAnalysis;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
    @Tag("sup, three")
    public void testApp() throws Exception
    {
//    	Injector injector = Guice.createInjector(new JavaBaconModule());
//    	injector.getInstance(BaconModule.Initialize.class);
//    	
//    	final DAOConcept daoConcept = injector.getInstance(DAOConcept.class);
//    	final DAOConceptLink daoConceptLink = injector.getInstance(DAOConceptLink.class);
//
//    	new TransactionWrapper<Void>(daoConcept) {
//
//			@Override
//			public Void executeTransactionCodeBlock() throws Exception {
//		    	Concept a = daoConcept.create(new JPAConcept("external systems", ""));
//		    	Concept b = daoConcept.create(new JPAConcept("hada", ""));
//		    	
//		    	daoConceptLink.create(new JPAConceptLink((JPAConcept) a, (JPAConcept) b));
//		    	
//		    	return null;
//			}
//		}.execute();
//		
//		ConceptService service = injector.getInstance(ConceptService.class);
//		
//		service.linkConcepts("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
//		service.linkConcepts("a", "b", "c", "d", "e", "f");
//		service.linkConcepts("four", "e", "d");
//		
//		System.out.println(service.getRelatedConcepts("four"));
//		System.out.println(service.getRelatedConcepts("three"));
//		
//		TagProcessor processor = injector.getInstance(TagProcessor.class);
//		processor.process(ClasspathHelper.forPackage("com.plural"));
		
		
		
//		Reflections reflections = new Reflections( 
//			    new ConfigurationBuilder().setUrls( 
//			    ClasspathHelper.forPackage( "com.plural.core" ) ).setScanners(
//			    new MethodAnnotationsScanner() ) );
//		
//		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Tag.class);
//	    
//		for(Class<?> c : classes) {
//			Tag tag = c.getAnnotation(Tag.class);
//			String[] concepts = tag.value().split(",", -1);
//			service.linkConcepts(concepts);
//		}
//		
//		
//		Set<Method> methods = reflections.getMethodsAnnotatedWith(Tag.class);
//    
//		for(Method m : methods) {
//			Tag tag = m.getAnnotation(Tag.class);
//			String[] concepts = tag.value().split(",", -1);
//			for(int i = 0; i < concepts.length; i++) {
//				concepts[i] = concepts[i].trim();
//			}
//			service.linkConcepts(concepts);
//		}
//		
//		System.out.println(service.getRelatedConcepts("three"));
//		
//		
//		 // creates an input stream for the file to be parsed
//        FileInputStream in = new FileInputStream("/home/francisco/Documents/Repositories/GIT/bacon/Bacon/sample/src/main/java/com/plural/java/Main.java");
//
//        CompilationUnit cu;
//        try {
//            // parse the file
//            cu = JavaParser.parse(in);
//        } finally {
//            in.close();
//        }
//
//        // visit and print the methods names
//        new MethodVisitor().visit(cu, null);
    	
    	BaconAnalysis result = new BaconAnalysis();
    	new StaticAnalyzer().process("/home/francisco/Documents/Repositories/GIT/bacon/Bacon/mayo/static", result);
		
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
