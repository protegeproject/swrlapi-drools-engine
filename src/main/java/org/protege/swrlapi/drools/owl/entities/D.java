package org.protege.swrlapi.drools.owl.entities;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.drools.extractors.DroolsOE2OWLEntityExtractor;
import org.protege.swrlapi.drools.swrl.BA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * This class represents an OWL datatype (e.g., xsd:int).
 */
public class D extends OE
{
	public D(String datatypeID)
	{
		super(datatypeID);
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public D(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof D) {
			D d = (D)ba;
			setID(d.getid());
		} else
			throw new RuntimeException("expecting OWL datatype from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	@Override
	public OWLDatatype extract(DroolsOE2OWLEntityExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
