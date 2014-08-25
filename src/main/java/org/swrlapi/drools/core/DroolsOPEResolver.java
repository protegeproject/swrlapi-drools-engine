package org.swrlapi.drools.core;

import org.swrlapi.drools.owl.properties.OPE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @see org.swrlapi.drools.owl.properties.OPE
 */
public class DroolsOPEResolver
{
	private final Map<String, OPE> pes;
	private int propertyExpressionIndex;

	public DroolsOPEResolver()
	{
		this.pes = new HashMap<String, OPE>();
		this.propertyExpressionIndex = 0;
	}

	public void reset()
	{
		this.pes.clear();
		this.propertyExpressionIndex = 0;
	}

	public boolean recordsPEID(String peid) { return this.pes.containsKey(peid); }

	public void record(OPE pe) { this.pes.put(pe.getid(), pe); }

	public Set<OPE> getPEs() { return new HashSet<OPE>(this.pes.values()); }

	public String generatePEID() { return "OPEID" + this.propertyExpressionIndex++; }
}
