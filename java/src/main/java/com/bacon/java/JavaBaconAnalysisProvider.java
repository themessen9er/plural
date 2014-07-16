package com.bacon.java;

import com.bacon.core.BaconAnalysisProvider;
import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.util.ProgressCallback;

public class JavaBaconAnalysisProvider implements BaconAnalysisProvider {

	private final StaticAnalyzer analizer = new StaticAnalyzer();
	private int progress = 0;
	
	@Override
	public synchronized BaconAnalysis analyze() {
		BaconAnalysis result = new BaconAnalysis();

//		new RuntimeAnalyzer().process(Sets.newHashSet(ClasspathHelper.forPackage("")), result);
		progress = 0;
		analizer.process("static", result, new ProgressCallback() {
			
			@Override
			public void onProgressChanged(int p) {
				progress = p;
				if(p == 100) {
					progress = 0;
				}
			}
		});

		return result;
	}

	@Override
	public int getProgress() {
		return progress;
	}

}
