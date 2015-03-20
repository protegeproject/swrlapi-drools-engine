package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL disjoint classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
 */
public class DCA extends DroolsBinaryClassesAxiom
{
	private static final long serialVersionUID = 1L;

	public DCA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	@Override
	public OWLDisjointClassesAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetSWRLRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public void visit(AVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "DCA" + super.toString();
	}
}