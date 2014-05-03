package com.bacon.core.entities;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Concept {

	private String name;
	
	private List<String> known;
	private Map<String, Long> usedWith;
	
	
	public Concept(String name) {
		this.name = name;
		this.known = Lists.newArrayList();
		this.usedWith = Maps.newHashMap();
	}
	
	public String getName() {
		return name;
	}
	
	public void addUsedWith(String name, Long strength) {
		known.add(name);
		usedWith.put(name, strength);
	}
	
	public List<String> getUsedWith() {
		return known;
	}
	
	public Long getStrength(String name) {
		return usedWith.get(name);
	}
	
}
