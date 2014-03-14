package org.swrlapi.drools.owl.entities;

import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.drools.extractors.DroolsOE2OWLEntityExtractor;
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

	public String getid()
	{
		return this.id;
	}

	protected void setID(String newID)
	{
		this.id = newID;
	}

	@Override
	public String toString()
	{
		return this.id;
	}

	public abstract OWLNamedObject extract(DroolsOE2OWLEntityExtractor extractor) throws TargetRuleEngineException;

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
		return (getid() == e.getid() || (getid() != null && getid().equals(e.getid())));
	}

	@Override
	public int hashCode()
	{
		int hash = 731;

		hash = hash + (null == getid() ? 0 : getid().hashCode());

		return hash;
	}
}
