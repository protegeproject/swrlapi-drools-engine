package org.protege.swrlapi.drools.owl.entities;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.drools.extractors.DroolsOE2OWLEntityExtractor;
import org.protege.swrlapi.drools.owl.expressions.CE;
import org.protege.swrlapi.drools.swrl.BA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Class representing an OWL named class in Drools. A named class is a type of class expression in OWL.
 */
public class C extends OE implements CE
{
	public C(String classID)
	{
		super(classID);
	}

	@Override
	public String getceid()
	{
		return super.getid();
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public C(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof C) {
			C c = (C)ba;
			setID(c.getid());
		} else
			throw new RuntimeException("expecting OWL class from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	@Override
	public OWLClass extract(DroolsOE2OWLEntityExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
