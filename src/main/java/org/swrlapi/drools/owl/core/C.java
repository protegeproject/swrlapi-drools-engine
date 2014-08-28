package org.swrlapi.drools.owl.core;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * Class representing an OWL named class in Drools. A named class is a type of class expression in OWL.
 *
 * @see org.semanticweb.owlapi.model.OWLClass
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
		return super.getName();
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public C(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof C) {
			C c = (C)ba;
			setId(c.getName());
		} else
			throw new TargetSWRLRuleEngineInternalException("expecting OWL class from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	@Override
	public OWLClass extract(DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	public static C getOWLThing()
	{
		return new C(OWLRDFVocabulary.OWL_THING.getShortForm());
	}

	public static C getOWLNothing()
	{
		return new C(OWLRDFVocabulary.OWL_NOTHING.getShortForm());
	}
}
