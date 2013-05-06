package com.plural.java;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ConfigurationBuilder;

import com.plural.core.services.ConceptService;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The TagProcessor implementation
 */
public class TagProcessorImpl implements TagProcessor {

	private ConceptService conceptService;

	/**
	 * The constructor
	 * @param conceptService An instance of {@link ConceptService}
	 */
	@Inject
	public TagProcessorImpl(
			ConceptService conceptService) {
		this.conceptService = conceptService;
	}
	
	/**
	 * Gets a {@link Reflections} instance for the specified {@link URL}s
	 * @param urls The url for the resources to scan
	 * @return A new instance of {@link Reflections}
	 */
	private Reflections getReflections(@Tag("parameter") Collection<URL> urls) {
		assert(urls != null && !urls.isEmpty());

		Reflections reflections;
		reflections = new Reflections( 
			    new ConfigurationBuilder().setUrls(urls).setScanners(
			    		new FieldAnnotationsScanner(), 
			    		new MethodParameterScanner(),
			    		new ResourcesScanner(), 
                        new TypeAnnotationsScanner(), 
                        new SubTypesScanner(),
			    		new MethodAnnotationsScanner(),
			    		new TypeElementsScanner()));
		return reflections;
	}
	
	@Override
	public void process(Set<URL> set) {
		assert(set != null);
		for(URL url : set) {
			process(url);
		}
	}
	
	@Override
	public void process(final URL url) {
		assert(url != null);
		
		Reflections r;
		r = getReflections(new ArrayList<URL>() {
			private static final long serialVersionUID = 1L;
			{add(url);}
		});
		
		processElements(r.getTypesAnnotatedWith(Tag.class));
//		processElements(r.getConstructorsAnnotatedWith(Tag.class));
		processElements(r.getMethodsAnnotatedWith(Tag.class));
		processElements(r.getFieldsAnnotatedWith(Tag.class));
	}

	/**
	 * Processes any kind of annotated element collection
	 * @param c The element collection
	 */
	private void processElements(Collection<? extends AnnotatedElement> c) {
		assert(c != null);

		for(AnnotatedElement e : c) {
			processElement(e);
		}
	}
	
	/**
	 * Processes any kind of annotated element
	 * @param e The element
	 */
	private void processElement(AnnotatedElement e) {
		assert(e.isAnnotationPresent(Tag.class));
		Tag tag = e.getAnnotation(Tag.class);
		String[] concepts = splitAndTrim(tag.value());
		conceptService.linkConcepts(concepts);
	}
	
	/**
	 * Splits and trims the given string containing the tags.
	 * @param tags The tags string
	 * @return An array with the trimmed tags
	 */
	private String[] splitAndTrim(String tags) {
		assert(tags != null);

		String[] concepts = tags.split(""+Tag.DELIMITER);
		for(int i = 0; i < concepts.length; i++) {
			concepts[i] = concepts[i].trim();
		}
		return concepts;
	}

}
