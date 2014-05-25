package org.swrlapi.drools.owl.entities;

import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.owl.OO;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL entity.
 */
public abstract class OE implements OO, AA, BA
{
	public String id;

	public OE(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.id;
	}

	protected void setId(String newId)
	{
		this.id = newId;
	}

	@Override
	public String toString()
	{
		// return "\"" + this.id + "\"";
		return this.id;
	}

	public abstract OWLNamedObject extract(DroolsOWLEntityExtractor extractor) throws TargetRuleEngineException;

	@Override
	public abstract SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor)
			throws TargetRuleEngineException;

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		OE e = (OE)obj;
		return (getName() == e.getName() || (getName() != null && getName().equals(e.getName())));
	}

	@Override
	public int hashCode()
	{
		int hash = 731;

		hash = hash + (null == getName() ? 0 : getName().hashCode());

		return hash;
	}
}
