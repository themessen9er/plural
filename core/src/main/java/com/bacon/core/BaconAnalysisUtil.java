package com.bacon.core;

import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.services.BaconAnalysis.Entry;
import com.bacon.core.services.ConceptService;
import com.google.common.collect.Lists;
import com.google.inject.Injector;

public class BaconAnalysisUtil {

	private static boolean busy = false;
	private static List<BaconAnalysisProvider> providers = Lists.newArrayList();
	
	
	public synchronized static void analize(Injector i) {
		busy = true;
		try {
			ConceptService service = i.getInstance(ConceptService.class);
			Reflections reflections = new Reflections();

			Set<Class<? extends BaconAnalysisProvider>> subTypes = 
					reflections.getSubTypesOf(BaconAnalysisProvider.class);

			providers = Lists.newArrayList();
			
			for(Class<? extends BaconAnalysisProvider> p : subTypes) {
				BaconAnalysisProvider instance = i.getInstance(p);
				providers.add(instance);
			}
			
			for(BaconAnalysisProvider p : providers) {
				BaconAnalysis analysis = p.analyze();
				process(analysis, service);
			}
			
		} finally{
			busy = false;
		}
	}

	private static void process(BaconAnalysis analysis, ConceptService service) {
		for(Entry e : analysis.getEntries()) {
			service.linkConcepts(e.getOccurrence(), e.getConcepts().toArray(new String[e.getConcepts().size()]));
		}
	}

	public static boolean isBusy() {
		return busy;
	}
	
	public static int getProgress() {
		int total = 0;
		for(BaconAnalysisProvider p : providers) {
			total += p.getProgress();
		}
		return providers.isEmpty() ? 0 : total / providers.size();
	}
	
}
