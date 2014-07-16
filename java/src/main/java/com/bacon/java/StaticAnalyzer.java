package com.bacon.java;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.bacon.core.entities.Occurrence;
import com.bacon.core.services.BaconAnalysis;
import com.bacon.core.services.BaconAnalysis.Entry;
import com.bacon.core.util.ProgressCallback;
import com.google.common.collect.Lists;

public class StaticAnalyzer {

	
	
	public void process(String staticDir, BaconAnalysis analysis, ProgressCallback callback) {

		ArrayList<File> files = Lists.newArrayList();
		listFiles(staticDir, files);

		FileInputStream in = null;
		
		callback.onProgressChanged(0);
		
		int i = 0;
		for(File f : files) {
			i++;
			try {
				in = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			CompilationUnit cu;
			try {
				// parse the file
				System.out.println("Parsing file " + f.getAbsolutePath());
				cu = JavaParser.parse(in);
				processCompilationUnit(f.getAbsolutePath(), cu, analysis);
			} catch (Throwable e) {
				System.out.println("Could not parse file " + f.getAbsolutePath());
			} finally {
				callback.onProgressChanged(i * 100 / files.size());
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		callback.onProgressChanged(100);
	}

	public void listFiles(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(".java")) {
				files.add(file);
			} else if (file.isDirectory()) {
				listFiles(file.getAbsolutePath(), files);
			}
		}
	}

	private void processCompilationUnit(final String location, final CompilationUnit cu,
			final BaconAnalysis analysis) {
		
		// visit and print the methods names
        new VoidVisitorAdapter<Object>(){
        	
        	public void visit(japa.parser.ast.body.AnnotationDeclaration n, Object arg) {
        		return;
        	};
        	
        	public void visit(japa.parser.ast.body.AnnotationMemberDeclaration n, Object arg) {
        		return;
        	};
        	
        	public void visit(japa.parser.ast.expr.SingleMemberAnnotationExpr n, Object arg) {
        		if("Tag".equals(n.getName().getName())) {
        			Occurrence o = new Occurrence();
        			o.setLocation(location);
        			o.setBeginLine(n.getBeginLine());
        			o.setBeginColumn(n.getBeginColumn());
        			o.setEndLine(n.getEndLine());
        			o.setEndColumn(n.getEndColumn());
        			
        			analysis.getEntries().add(new Entry(
        					Lists.newArrayList(splitAndTrim(n.getMemberValue().toString())),
        					o));
        		}
        	};
        	
        }.visit(cu, null);
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
			concepts[i] = concepts[i].replace('"', ' ').trim();
		}
		return concepts;
	}

}
