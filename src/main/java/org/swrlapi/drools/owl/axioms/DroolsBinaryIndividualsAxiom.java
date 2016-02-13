package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.individuals.I;

abstract class DroolsBinaryIndividualsAxiom extends DroolsBinaryObject<I, I> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsBinaryIndividualsAxiom(I individual1, I individual2)
  {
    super(individual1, individual2);
  }

  public I geti1()
  {
    return getT1();
  }

  public I geti2()
  {
    return getT2();
  }

  public String geti1id()
  {
    return getT1().getid();
  }

  public String geti2id()
  {
    return getT2().getid();
  }
}