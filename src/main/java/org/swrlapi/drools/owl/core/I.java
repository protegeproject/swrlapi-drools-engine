package org.swrlapi.drools.owl.core;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsOWLIndividualExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

// TODO This class represents both a named an anonymous individual 
// - but an anonymous individual should not be a subclass of an OWL entity.

/**
 * This class represents an OWL named or anonymous individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 */
public class I extends OE
{
	private static final long serialVersionUID = 1L;

	public I(String id)
	{
		super(id);
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public I(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof I) {
			I i = (I)ba;
			setId(i.getName());
		} else
			throw new TargetSWRLRuleEngineInternalException("expecting OWL individual from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	public OWLIndividual extract(DroolsOWLIndividualExtractor extractor)
	{
		return extractor.extract(this);
	}

	@Override
	public OWLNamedIndividual extract(DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
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
}
