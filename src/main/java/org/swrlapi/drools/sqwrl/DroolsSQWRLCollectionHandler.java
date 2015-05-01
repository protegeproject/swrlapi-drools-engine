package org.swrlapi.drools.sqwrl;

import java.util.HashSet;
import java.util.Set;

import org.swrlapi.drools.swrl.BA;

/**
 * Keeps track of SQWRL collections during rule execution.
 * <p>
 * The {@link #infer(org.swrlapi.drools.swrl.BA)} method in this class is called to inform Drools of inferred SQWRL
 * collections.
 *
 * @see org.swrlapi.drools.sqwrl.SQWRLC
 */
public class DroolsSQWRLCollectionHandler
{
  private final Set<SQWRLC> generatedSQWRLCollections;

  public DroolsSQWRLCollectionHandler()
  {
    this.generatedSQWRLCollections = new HashSet<>();
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
