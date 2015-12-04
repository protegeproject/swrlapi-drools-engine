package org.swrlapi.drools.swrl;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
import org.swrlapi.drools.core.DroolsNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the names of variables passed to built-ins. Non variable arguments positions are represented by
 * empty strings.
 */
public class BAVNs
{
  public static final int MaxArguments = 10;

  @NonNull private final List<String> variableNames = new ArrayList<>();

  /*
   * The following vararg constructor does not seem to work in this version of Drools
   * 
   * public BAVNs(String... variableNames) { for (String variableName : variableNames) {
   * this.variableNames.add(variableName); } }
   */

  public BAVNs()
  {
  }

  public BAVNs(@NonNull String v1)
  {
    this.variableNames.add(v1);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5,
    @NonNull String v6)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
    this.variableNames.add(v6);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5,
    @NonNull String v6, @NonNull String v7)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
    this.variableNames.add(v6);
    this.variableNames.add(v7);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5,
    @NonNull String v6, @NonNull String v7, @NonNull String v8)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
    this.variableNames.add(v6);
    this.variableNames.add(v7);
    this.variableNames.add(v8);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5,
    @NonNull String v6, @NonNull String v7, @NonNull String v8, @NonNull String v9)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
    this.variableNames.add(v6);
    this.variableNames.add(v7);
    this.variableNames.add(v8);
    this.variableNames.add(v9);
  }

  public BAVNs(@NonNull String v1, @NonNull String v2, @NonNull String v3, @NonNull String v4, @NonNull String v5,
    @NonNull String v6, @NonNull String v7, @NonNull String v8, @NonNull String v9, @NonNull String v10)
  {
    this.variableNames.add(v1);
    this.variableNames.add(v2);
    this.variableNames.add(v3);
    this.variableNames.add(v4);
    this.variableNames.add(v5);
    this.variableNames.add(v6);
    this.variableNames.add(v7);
    this.variableNames.add(v8);
    this.variableNames.add(v9);
    this.variableNames.add(v10);
  }

  @NonNull public List<String> getVariableNames()
  {
    return this.variableNames;
  }

  public int getNumberOfArguments()
  {
    return this.variableNames.size();
  }

  public boolean hasVariableNames()
  {
    return getNumberOfArguments() != 0;
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    String representation = DroolsNames.BUILT_IN_VARIABLE_NAMES_CLASS_NAME + "(";
    boolean isFirst = true;

    for (String variableName : this.variableNames) {
      if (!isFirst)
        representation += ", ";
      else
        isFirst = false;
      representation += variableName;
    }
    representation += ")";
    return representation;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    BAVNs avns = (BAVNs)obj;
    return (getVariableNames() == avns.getVariableNames() || (getVariableNames() != null && getVariableNames()
      .equals(avns.getVariableNames())));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 66;

    hash = hash + (null == getVariableNames() ? 0 : getVariableNames().hashCode());

    return hash;
  }
}
