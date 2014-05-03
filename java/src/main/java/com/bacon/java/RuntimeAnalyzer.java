package com.bacon.java;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ConfigurationBuilder;

import com.bacon.core.entities.Occurrence;
import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.services.BaconAnalysis.Entry;
import com.google.common.collect.Lists;

public class RuntimeAnalyzer {

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

	public void process(Set<URL> set, BaconAnalysis analysis) {
		assert(set != null);
		for(URL url : set) {
			process(url, analysis);
		}
	}

	public void process(final URL url, BaconAnalysis analysys) {
		assert(url != null);

		Reflections r;
		r = getReflections(new ArrayList<URL>() {
			private static final long serialVersionUID = 1L;
			{add(url);}
		});

		processElements(r.getTypesAnnotatedWith(Tag.class), analysys);
		//		processElements(r.getConstructorsAnnotatedWith(Tag.class));
		processElements(r.getMethodsAnnotatedWith(Tag.class), analysys);
		processElements(r.getFieldsAnnotatedWith(Tag.class), analysys);
	}

	/**
	 * Processes any kind of annotated element collection
	 * @param c The element collection
	 */
	private void processElements(Collection<? extends AnnotatedElement> c, BaconAnalysis analysis) {
		assert(c != null);

		for(AnnotatedElement e : c) {
			processElement(e, analysis);
		}
	}

	/**
	 * Processes any kind of annotated element
	 * @param e The element
	 */
	private void processElement(AnnotatedElement e, BaconAnalysis analysis) {
		assert(e.isAnnotationPresent(Tag.class));
		Tag tag = e.getAnnotation(Tag.class);
		String[] concepts = splitAndTrim(tag.value());
		Occurrence o = new Occurrence();
		analysis.getEntries().add(new Entry(Lists.newArrayList(concepts), o));
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
