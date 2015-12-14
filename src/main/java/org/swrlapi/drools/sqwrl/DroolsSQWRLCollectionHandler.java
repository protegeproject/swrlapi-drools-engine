package org.swrlapi.drools.sqwrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.swrl.BA;

import java.util.HashSet;
import java.util.Set;

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
  @NonNull private final Set<@NonNull SQWRLC> generatedSQWRLCollections;

  public DroolsSQWRLCollectionHandler()
  {
    this.generatedSQWRLCollections = new HashSet<>();
  }

  public void reset()
  {
    this.generatedSQWRLCollections.clear();
  }

  public void infer(@NonNull BA ba)
  {
    if (ba instanceof SQWRLC)
      this.generatedSQWRLCollections.add((SQWRLC)ba);
  }

  public boolean hasSQWRLCollections()
  {
    return !this.generatedSQWRLCollections.isEmpty();
  }

  @NonNull public Set<@NonNull SQWRLC> getSQWRLCollections()
  {
    return this.generatedSQWRLCollections;
  }
}
