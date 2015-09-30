package test;

import java.io.File;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import de.derivo.sparqldlapi.Query;
import de.derivo.sparqldlapi.QueryEngine;
import de.derivo.sparqldlapi.QueryResult;
import de.derivo.sparqldlapi.exceptions.QueryEngineException;
import de.derivo.sparqldlapi.exceptions.QueryParserException;
import model.MyFactory;

public class Sparqldl implements ITestCase {
	// the manager
	private OWLOntologyManager manager;
	// File with an existing ontology!
	private File fClass;
	// the ontology
	private OWLOntology ontology;
	// ontology for Abox, Tbox, Rbox
	private OWLOntology ontlgAbox, ontlgTbox, ontlgRbox;
	// Factory
	private MyFactory factory;
	// The reasoner based on the ontology.
	private OWLReasoner hermit;
	// the sparqldl query engine
	private QueryEngine sparqldl;
	
	@Override
	public boolean prepare() {
		// Create the manager
		manager = OWLManager.createOWLOntologyManager();
		// File with an existing ontology - make sure it's there!
		fClass = new File("model/mcs_ontology.owl");
		// Load the ontology from the file
		try {
			ontology = manager.loadOntologyFromOntologyDocument(fClass);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// create factory
		factory = new MyFactory(ontology);
		// create reasoner based on ontology.
		hermit = new Reasoner.ReasonerFactory().createReasoner(ontology);
		// create queryengine
		sparqldl = QueryEngine.create(manager, hermit, true);
		return true;
	}

	@Override
	public boolean test() {
		Query query = null;
		QueryResult result = null;
		try {
			query = Query.create("SELECT * WHERE { Individual(<http://www.semanticweb.org/serene/ontologies/2015/IRIT/Project/AgileDoctor#Jing>) }");
		} catch (QueryParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result = sparqldl.execute(query);
		} catch (QueryEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.toJSON());
		return true;
	}

}
