package com.bacon.mayo.ws.entities;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value="WSConcept", description="A concept...")
@XmlRootElement
public class WSConcept {
	
	public String name;
	public Long strength;
	public Collection<String> imports;

}
