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

  @NonNull public I geti1()
  {
    return getT1();
  }

  @NonNull public I geti2()
  {
    return getT2();
  }

  @NonNull public String geti1id()
  {
    return getT1().getid();
  }

  @NonNull public String geti2id()
  {
    return getT2().getid();
  }
}