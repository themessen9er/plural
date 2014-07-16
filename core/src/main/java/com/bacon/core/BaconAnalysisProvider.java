package com.bacon.core;

import com.bacon.core.services.BaconAnalysis;

public interface BaconAnalysisProvider {

	public BaconAnalysis analyze();
	
	public int getProgress();
	
}
