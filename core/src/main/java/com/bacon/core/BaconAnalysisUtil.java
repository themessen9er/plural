package com.bacon.core;

import java.util.Set;

import org.reflections.Reflections;

import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.services.BaconAnalysis.Entry;
import com.bacon.core.services.ConceptService;
import com.google.inject.Injector;

public class BaconAnalysisUtil {

	public static void analize(Injector i) {

		ConceptService service = i.getInstance(ConceptService.class);
		Reflections reflections = new Reflections();

		Set<Class<? extends BaconAnalysisProvider>> subTypes = 
				reflections.getSubTypesOf(BaconAnalysisProvider.class);

		for(Class<? extends BaconAnalysisProvider> p : subTypes) {
			BaconAnalysisProvider instance = i.getInstance(p);
			BaconAnalysis analysis = instance.analyze();
			process(analysis, service);
		}
	}

	private static void process(BaconAnalysis analysis, ConceptService service) {
		for(Entry e : analysis.getEntries()) {
			service.linkConcepts(e.getOccurrence(), e.getConcepts().toArray(new String[e.getConcepts().size()]));
		}
	}

}
