package org.swrlapi.drools.owl.literals;

import java.util.Objects;

/** Internal datatype membership inferred by range and universal-restriction rules. */
public final class LiteralTypeAssertion
{
  private final L literal;
  private final String datatype;
  public LiteralTypeAssertion(L literal, String datatype) { this.literal = literal; this.datatype = datatype; }
  public L getLiteral() { return literal; }
  public String getDatatype() { return datatype; }
  @Override public boolean equals(Object other) {
    if (!(other instanceof LiteralTypeAssertion)) return false;
    LiteralTypeAssertion that = (LiteralTypeAssertion)other;
    return literal.equals(that.literal) && datatype.equals(that.datatype);
  }
  @Override public int hashCode() { return Objects.hash(literal, datatype); }
}
