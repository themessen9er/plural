package com.bacon.mayo.ws.entities;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value="WSOccurrence", description = "Represents an occurrence of a concept in a source file")
public class WSOccurrence {

	public String location;
	public int begin_line;
	public int begin_column;
	public int end_line;
	public int end_column;

	public String occurrence_excerpt;
	public String full_resource;
	
	
}
