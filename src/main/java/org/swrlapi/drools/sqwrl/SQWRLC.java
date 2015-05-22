package org.swrlapi.drools.sqwrl;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents a SQWRL collection in Drools. These are created by SQWRL collection construction operators and
 * passed to the second phase of rule execution via the {@link org.swrlapi.drools.sqwrl.DroolsSQWRLCollectionHandler}.
 *
 * @see org.swrlapi.drools.sqwrl.DroolsSQWRLCollectionHandler
 */
public class SQWRLC implements BA
{
  private final String variableName, queryName, collectionName, collectionID;

  public SQWRLC(String variableName, String queryName, String collectionName, String collectionID)
  {
    this.variableName = variableName;
    this.queryName = queryName;
    this.collectionName = collectionName;
    this.collectionID = collectionID;
  }

  public String getVariableName()
  {
    return this.variableName;
  }

  public String getQueryName()
  {
    return this.queryName;
  }

  public String getCollectionName()
  {
    return this.collectionName;
  }

  public String getCollectionID()
  {
    return this.collectionID;
  }

  @NonNull @Override
  public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public String toString()
  {
    return "SQWRLC(" + getQueryName() + ", " + getCollectionName() + ", " + getCollectionID() + ")";
  }

  @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    SQWRLC sqwrlc = (SQWRLC)o;

    if (variableName != null ? !variableName.equals(sqwrlc.variableName) : sqwrlc.variableName != null)
      return false;
    if (queryName != null ? !queryName.equals(sqwrlc.queryName) : sqwrlc.queryName != null)
      return false;
    if (collectionName != null ? !collectionName.equals(sqwrlc.collectionName) : sqwrlc.collectionName != null)
      return false;
    return !(collectionID != null ? !collectionID.equals(sqwrlc.collectionID) : sqwrlc.collectionID != null);

  }

  @Override public int hashCode()
  {
    int result = variableName != null ? variableName.hashCode() : 0;
    result = 31 * result + (queryName != null ? queryName.hashCode() : 0);
    result = 31 * result + (collectionName != null ? collectionName.hashCode() : 0);
    result = 31 * result + (collectionID != null ? collectionID.hashCode() : 0);
    return result;
  }
}
