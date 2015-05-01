package org.swrlapi.drools.owl.dataranges;

// TODO Facets are not represented

/**
 * Class representing an OWL datatype restriction data range
 *
 * @see org.semanticweb.owlapi.model.OWLDatatypeRestriction
 */
public class DRR implements DR
{
  private static final long serialVersionUID = 1L;

  private final String rid;

  public DRR(String rid)
  {
    this.rid = rid;
  }

  @Override
  public String getrid()
  {
    return this.rid;
  }
}
