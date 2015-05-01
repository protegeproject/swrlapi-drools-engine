package org.swrlapi.drools.owl.dataranges;

/**
 * Class representing an OWL data complement of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataComplementOf
 */
public class DCO implements DR
{
  private static final long serialVersionUID = 1L;

  private final String rid;
  private final String complementRangeID;

  public DCO(String rid, String complementRangeID)
  {
    this.rid = rid;
    this.complementRangeID = complementRangeID;
  }

  @Override
  public String getrid()
  {
    return this.rid;
  }

  public String getComplementRangeID()
  {
    return this.complementRangeID;
  }
}
