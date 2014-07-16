package com.bacon.mayo.ws.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bacon.core.entities.Concept;
import com.bacon.core.entities.Occurrence;
import com.bacon.core.services.ConceptService;
import com.bacon.mayo.ws.ServicePaths;
import com.bacon.mayo.ws.entities.WSConcept;
import com.bacon.mayo.ws.entities.WSOccurrence;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value=ServicePaths.CONCEPTS, description="The service used to manage concepts")
@Path(ServicePaths.CONCEPTS)
public class WSConceptService {

	protected final ConceptService conceptService;

	@Inject
	public WSConceptService(
			ConceptService conceptService) {

		this.conceptService = conceptService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Gets the links between concepts",
			response=WSConcept.class,
			responseContainer="List")
	public List<WSConcept> getLinks() {
		List<Concept> concepts = conceptService.getAllConcepts();
		List<WSConcept> result = new ArrayList<WSConcept>();

		for(Concept c : concepts) {
			WSConcept wsc = new WSConcept();
			wsc.name = c.getName();
			wsc.imports = c.getUsedWith();
			result.add(wsc);
		}

		return result;
	}

	@GET
	@Path("/{concept}/occurrences")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Gets the occurrences of concepts in source files",
			response=WSOccurrence.class,
			responseContainer="List")
	public List<WSOccurrence> getOccurrences(@PathParam("concept") String concept) {
		List<WSOccurrence> result = Lists.newArrayList();
		List<Occurrence> occurrences = conceptService.getOccurrences(concept);

		for(Occurrence o : occurrences) {
			WSOccurrence wso = new WSOccurrence();
			wso.location = o.getLocation();
			wso.begin_line = o.getBeginLine();
			wso.begin_column = o.getBeginColumn();
			wso.end_line = o.getEndLine();
			wso.end_column = o.getEndColumn();
			wso.occurrence_excerpt = getExcerpt(o);
			wso.full_resource = getResource(o.getLocation());

			result.add(wso);
		}

		return result;
	}

	private String getExcerpt(Occurrence o) {
		StringBuilder b = new StringBuilder();
		BufferedReader br = null;
		String ls = System.getProperty("line.separator");

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(o.getLocation()));

			int lineNumber = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				if(lineNumber >= (o.getBeginLine() - 5) && lineNumber <= (o.getEndLine() + 5)) {
					b.append(sCurrentLine);
					b.append(ls);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return b.toString();
	}

	private String getResource(String location) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader( new FileReader (location));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
			while( ( line = reader.readLine() ) != null ) {
			    stringBuilder.append( line );
			    stringBuilder.append( ls );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	    return stringBuilder.toString();
	}

}
