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
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.bacon.core.BaconAnalysisProvider;
import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.services.BaconAnalysis.Entry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class JavaBaconAnalysisProvider implements BaconAnalysisProvider {

	@Tag("something, shit, damn")
	@Override
	public BaconAnalysis analyze() {
		BaconAnalysis result = new BaconAnalysis();

//		new RuntimeAnalyzer().process(Sets.newHashSet(ClasspathHelper.forPackage("")), result);
		new StaticAnalyzer().process("static", result);

		return result;
	}

	

}
