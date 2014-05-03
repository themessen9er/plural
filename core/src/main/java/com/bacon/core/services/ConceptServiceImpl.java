package com.bacon.core.services;

import java.util.Collection;
import java.util.List;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import com.bacon.core.Relationships;
import com.bacon.core.entities.Concept;
import com.bacon.core.entities.Occurrence;
import com.google.common.collect.Lists;

public class ConceptServiceImpl implements ConceptService {

	private final GraphDatabaseService graphDb;


	public ConceptServiceImpl() {
		this.graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("new4jDB");
	}


	@Override
	public void linkConcepts(Occurrence o, String... relatedConcepts) {
		try ( Transaction tx = graphDb.beginTx() ) {

			Label label = DynamicLabel.label("concept");

			for(String c : relatedConcepts) {
				
				if(c == null) {
					continue;
				}
				
				Node firstNode = getConcept(c);
				if(firstNode == null) {
					firstNode = graphDb.createNode(label);
					firstNode.setProperty( "name", c );
				}

				//Relate to other concepts
				for(String c2 : relatedConcepts) {
					if(!c.equals(c2)) {

						Node secondNode = getConcept(c2);
						if(secondNode == null) {
							secondNode = graphDb.createNode(label);
							secondNode.setProperty( "name", c2 );
						}

						Relationship rel = getRelationship(firstNode, secondNode, Relationships.USED_WITH);

						if(rel != null) {
							Long strength = (Long) rel.getProperty("strength", new Long(0));
							rel.setProperty("strength", strength+1);
						} else {
							Relationship relationship = firstNode.createRelationshipTo( secondNode, Relationships.USED_WITH );
							relationship.setProperty( "strength", new Long(1) );
						}
					}
				}

				//Register occurrence
				Label oLabel = DynamicLabel.label("occurrence");

				Node oNode = getConcept(c);
				oNode = graphDb.createNode(oLabel);
				oNode.setProperty( "location", o.getLocation() );
				oNode.setProperty( "begin_line", o.getBeginLine() );
				oNode.setProperty( "begin_column", o.getBeginColumn() );
				oNode.setProperty( "end_line", o.getEndLine() );
				oNode.setProperty( "end_column", o.getEndColumn() );

				Relationship or = firstNode.createRelationshipTo(oNode, Relationships.OCCURS);

			}
			tx.success();
			tx.close();
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
	public Collection<String> getRelatedConcepts(String concept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> search(String s) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Concept> getAllConcepts() {
		List<Concept> result = Lists.newArrayList();
		try ( Transaction tx = graphDb.beginTx() ) {

			ResourceIterable<Node> iterable = GlobalGraphOperations.at(graphDb).getAllNodesWithLabel(DynamicLabel.label("concept"));
			for(Node n : iterable) {
				Concept c = new Concept(n.getProperty("name").toString());
				for(Relationship r : n.getRelationships(Relationships.USED_WITH)){
					Node endnode = r.getEndNode();
					c.addUsedWith(endnode.getProperty("name").toString(), new Long(1));
				}
				result.add(c);
			}

			tx.close();
		}
		return result;
	}

	public Node getConcept(String name) {
		for(Node n : graphDb.findNodesByLabelAndProperty(DynamicLabel.label("concept"), "name", name)) {
			return n;
		}
		return null;
	}

	public Relationship getRelationship(Node a, Node b, RelationshipType type) {
		for(Relationship r :a.getRelationships(type)) {
			if (r.getOtherNode(a).equals(b)) return r;
		}
		return null;
	}


	@Override
	public List<Occurrence> getOccurrences(String concept) {
		List<Occurrence> result = Lists.newArrayList();
		try ( Transaction tx = graphDb.beginTx() ) {

			Label label = DynamicLabel.label("concept");
			ResourceIterable<Node> iterable = graphDb.findNodesByLabelAndProperty(label, "name", concept);

			for(Node n : iterable) {
				Occurrence o = new Occurrence();
				for(Relationship r : n.getRelationships(Relationships.OCCURS)){
					Node endnode = r.getEndNode();
					o.setLocation((String) endnode.getProperty("location"));
					o.setBeginLine((int) endnode.getProperty("begin_line"));
					o.setBeginColumn((int) endnode.getProperty("begin_column"));
					o.setEndLine((int) endnode.getProperty("end_line"));
					o.setEndColumn((int) endnode.getProperty("end_column"));
					result.add(o);
				}
			}

			tx.close();
		}
		return result;
	}

}
