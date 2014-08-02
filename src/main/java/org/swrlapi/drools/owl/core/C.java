package org.swrlapi.drools.owl.core;

import org.semanticweb.owlapi.model.OWLClass;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetRuleEngineException;

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
			throw new RuntimeException("expecting OWL class from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	@Override
	public OWLClass extract(DroolsOWLEntityExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	public static C getOWLThing() { return new C("owl:Thing"); }

	public static C getOWLNothing() { return new C("owl:Nothing"); }
}
