package test;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import model.Child;
import model.MyFactory;

public class IndividualCrudInRam implements ITestCase {
	// the manager
	private OWLOntologyManager manager;
	// File with an existing ontology!
	private File fClass;
	// the ontology
	private OWLOntology ontology;
	// Factory
	private MyFactory factory;

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
		factory = new MyFactory(ontology);
		return true;
	}

	@Override
	public boolean test() {
		// Check if the ontology contains any axioms
		System.out.println("Number of axioms: " + ontology.getAxiomCount());
		// test of CRUD
		// test of Create
		System.out.println("Number of children: " + factory.getAllChildInstances().size());
		System.out.println("Create a new child ");
		factory.createChild("Nicola");
		System.out.println("Number of children: " + factory.getAllChildInstances().size());
		// test of Read
		Child c = factory.getChild("Nicola");
		System.out.println(c.getOwlIndividual());
		//TODO: test of Update
		
		// test of Delete
		c.delete();
		System.out.println("Number of children: " + factory.getAllChildInstances().size());
		return true;
	}

}
