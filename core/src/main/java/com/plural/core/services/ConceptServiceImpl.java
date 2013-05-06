package com.plural.core.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.plural.core.persistence.Concept;
import com.plural.core.persistence.ConceptLink;
import com.plural.core.persistence.DAO;
import com.plural.core.persistence.DAOConcept;
import com.plural.core.persistence.DAOConceptLink;
import com.plural.core.persistence.TransactionWrapper;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The implementation of {@link ConceptService}
 */
public class ConceptServiceImpl implements ConceptService {

	private DAOConcept daoConcept;
	private DAOConceptLink daoConceptLink;
	
	
	/**
	 * The class constructor
	 * @param daoConcept The {@link DAO} for {@link Concept}
	 * @param daoConceptLink The {@link DAO} for {@link ConceptLink}
	 */
	@Inject
	public ConceptServiceImpl(
			DAOConcept daoConcept, 
			DAOConceptLink daoConceptLink) {
		this.daoConcept = daoConcept;
		this.daoConceptLink = daoConceptLink;
	}
	
	@Override
	public void linkConcepts(final String... relatedConcepts) {
		try {
			new TransactionWrapper<Void>(daoConcept) {

				@Override
				protected Void executeTransactionCodeBlock() throws Exception {
					int totalLength = relatedConcepts.length;
					Concept[] concepts = new Concept[totalLength];
					
					for(int i = 0; i < totalLength; i++) {
						String name = relatedConcepts[i];
						Concept c = daoConcept.create(name, null);
						concepts[i] = c;
					}
					
					for(int i = 0; i < totalLength - 1; i++) {
						Concept cA = concepts[i];
						
						for(int j = i + 1; j < totalLength; j++) {
							Concept cB = concepts[j];
							daoConceptLink.create(cA, cB);
						}
					}
					return null;
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void unlinkConcept(String concept, String... relatedConcepts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteConcepts(String... concepts) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getRelatedConcepts(final String conceptName) {
		Concept concept = daoConcept.get(conceptName);
		Collection<ConceptLink> links = daoConceptLink.getLinksForConcept(concept);
		List<String> result = new ArrayList<String>();
		
		for(ConceptLink l : links) {
			result.add(l.getLinkedConcept(concept).getName());
		}
		
		return result;
	}

	@Override
	public Collection<String> search(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
