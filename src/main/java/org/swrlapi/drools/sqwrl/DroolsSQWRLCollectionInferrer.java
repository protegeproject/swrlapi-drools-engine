
package org.swrlapi.drools.sqwrl;

import java.util.HashSet;
import java.util.Set;

import org.swrlapi.drools.swrl.BA;

/**
 * This {@link #infer(SQWRLC)} method in this class is called to inform Drools of inferred SQWRL collections
 */
public class DroolsSQWRLCollectionInferrer
{
	private final Set<SQWRLC> generatedSQWRLCollections;

	public DroolsSQWRLCollectionInferrer()
	{
		this.generatedSQWRLCollections = new HashSet<SQWRLC>();
	}

	public void reset()
	{
		this.generatedSQWRLCollections.clear();
	}

	public void infer(BA ba)
	{
		if (ba instanceof SQWRLC)
			this.generatedSQWRLCollections.add((SQWRLC)ba);
	}

	public boolean hasSQWRLCollections()
	{
		return !this.generatedSQWRLCollections.isEmpty();
	}

	public Set<SQWRLC> getSQWRLCollections()
	{
		return this.generatedSQWRLCollections;
	}
}
