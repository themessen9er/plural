package com.bacon.core.services;

import java.util.List;

import com.bacon.core.entities.Occurrence;
import com.google.common.collect.Lists;

public class BaconAnalysis {

	public static class Entry {

		private List<String> concepts;
		private Occurrence occurrence;
		//TODO resource identification

		public Entry(List<String> concepts, Occurrence occurrence) {
			this.concepts = concepts;
			this.occurrence = occurrence;
		}
		
		public List<String> getConcepts() {
			return concepts;
		}
		
		public Occurrence getOccurrence() {
			return occurrence;
		}

	}


	private List<Entry> entries;

	public BaconAnalysis() {
		entries = Lists.newArrayList();
	}
	
	public List<Entry> getEntries() {
		return this.entries;
	}

}
