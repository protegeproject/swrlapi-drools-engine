package org.swrlapi.drools.core;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.OWLClassExpressionResolver;
import org.swrlapi.drools.owl.classexpressions.*;
import org.swrlapi.exceptions.TargetRuleEngineException;

import java.util.*;

/**
 *
 * @see org.swrlapi.drools.owl.classexpressions.CE
 */
public class DroolsCEResolver
{
	private final Map<String, CE> ces;
	private int classExpressionIndex;

	public DroolsCEResolver()
	{
		this.ces = new HashMap<String, CE>();
		this.classExpressionIndex = 0;
	}

	public void reset()
	{
		this.ces.clear();
		this.classExpressionIndex = 0;
	}

	public boolean recordsCEID(String ceid) { return this.ces.containsKey(ceid); }

	public void record(CE ce) { this.ces.put(ce.getceid(), ce); }

	public Set<CE> getCEs() { return new HashSet<CE>(this.ces.values()); }

	public String generateCEID() { return "CEID" + this.classExpressionIndex++; }
}
