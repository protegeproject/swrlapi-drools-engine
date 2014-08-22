package org.swrlapi.drools.core;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.OWLClassExpressionResolver;
import org.swrlapi.drools.owl.classexpressions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see org.swrlapi.drools.owl.classexpressions.CE
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
public class CEResolver
{
	private final Map<OWLClassExpression, String> owlClassExpression2CEIDMap;
	private final Set<String> convertedClassExpressionIDs;
	private final Set<CE> ces;
	private int classExpressionIndex;

	public CEResolver()
	{
		this.classExpressionIndex = 0;
		this.ces = new HashSet<CE>();
		this.owlClassExpression2CEIDMap = new HashMap<OWLClassExpression, String>();
		this.convertedClassExpressionIDs = new HashSet<String>();
	}

	public void reset()
	{
		this.classExpressionIndex = 0;
		this.ces.clear();
		this.owlClassExpression2CEIDMap.clear();
		this.convertedClassExpressionIDs.clear();
	}

	public Set<CE> getCEs()
	{
		return this.ces;
	}

	private String getCEID(OWLClassExpression classExpression)
	{
		if (this.owlClassExpression2CEIDMap.containsKey(classExpression))
			return this.owlClassExpression2CEIDMap.get(classExpression);
		else {
			String classExpressionID = "CEID" + this.classExpressionIndex++;
			this.owlClassExpression2CEIDMap.put(classExpression, classExpressionID);
			return classExpressionID;
		}
	}
}
