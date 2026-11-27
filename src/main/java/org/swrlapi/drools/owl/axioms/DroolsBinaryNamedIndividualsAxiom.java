package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.individuals.I;

abstract class DroolsBinaryNamedIndividualsAxiom extends DroolsBinaryObject<I, I> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsBinaryNamedIndividualsAxiom(I individual1, I individual2)
  {
    super(individual1, individual2);
  }

  public @NonNull I geti1()
  {
    return getT1();
  }

  public @NonNull I geti2()
  {
    return getT2();
  }

  public @NonNull String geti1id()
  {
    return getT1().getid();
  }

  public @NonNull String geti2id()
  {
    return getT2().getid();
  }
}