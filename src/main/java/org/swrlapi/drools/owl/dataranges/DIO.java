package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data intersection of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataIntersectionOf
 */
public class DIO implements DR
{
  private static final long serialVersionUID = 1L;

  private final String rid;
  private final Set<String> dataRangeIDs;

  public DIO(String rid, Set<String> dataRangeIDs)
  {
    this.rid = rid;
    this.dataRangeIDs = dataRangeIDs;
  }

  @Override
  public String getrid()
  {
    return this.rid;
  }

  public Set<String> getDataRangeIDs()
  {
    return this.dataRangeIDs;
  }
}
