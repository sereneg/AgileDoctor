package test;

import java.io.File;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import model.MyFactory;

public class UseReasoner implements ITestCase {
	// Onotology Manager, all ontologies are controlled by the manager.
	private OWLOntologyManager manager;
	// File with an existing ontology!
	private File fClass;
	// IRI for ontology
	IRI iriClass;
	// the ontology used in the project.
	private OWLOntology ontology;
	// Factory is used to create new individual based on ontology.
	private MyFactory factory;
	// The reasoner based on the ontology.
	private OWLReasoner hermit;
	private Reasoner hermitPrivate;

	@Override
	public boolean prepare() {
		// Create the manager
		manager = OWLManager.createOWLOntologyManager();
		// File with an existing ontology - make sure it's there!
		fClass = new File("model/mcs_ontology.owl");
		// create IRI from file.
		iriClass = IRI.create(fClass);
		// Load the ontology from the file
		try {
			ontology = manager.loadOntologyFromOntologyDocument(iriClass);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// create reasoner based on ontology.
		hermit = new Reasoner.ReasonerFactory().createReasoner(ontology);
		hermitPrivate = new Reasoner(ontology);
		// create factory of ontology
		factory = new MyFactory(ontology);
		return true;
	}

	@Override
	public boolean test() {
		// test reasoner.
		System.out.println("Reasoner name: " + hermit.getReasonerName());
		System.out.println("Ontology Consistent: " + hermit.isConsistent());
		System.out.println("Reasoner version: " + hermit.getReasonerVersion().toString());
		System.out.println("Reasoner name: " + hermitPrivate.getReasonerName());
		System.out.println("Ontology Consistent: " + hermitPrivate.isConsistent());
		System.out.println("Reasoner version: " + hermitPrivate.getReasonerVersion().toString());
		return true;
	}

}
