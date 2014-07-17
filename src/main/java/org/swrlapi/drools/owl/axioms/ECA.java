package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL equivalent classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
 */
public class ECA extends DroolsBinaryObject<String, String> implements A
{
	public ECA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public String getc1id() { return getT1(); }

	public String getc2id() { return getT2(); }

	@Override
	public OWLEquivalentClassesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "ECA" + super.toString();
	}
}