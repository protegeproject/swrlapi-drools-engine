package org.swrlapi.drools.owl.literals;

import org.junit.Test;
import static org.junit.Assert.*;

public class LiteralValuesTest
{
  @Test public void invalidLexicalFormsAreRejected() {
    for (L literal : new L[]{new L("1e2", "xsd:decimal"), new L("1.1", "xsd:integer"), new L("TRUE", "xsd:boolean"),
      new L("-1", "xsd:unsignedInt"), new L("128", "xsd:byte"), new L("-129", "xsd:byte"),
      new L("18446744073709551616", "xsd:unsignedLong"), new L("2026-02-30T12:00:00Z", "xsd:dateTime"),
      new L("2026-01-01T12:00:00", "xsd:dateTimeStamp"), new L("xy", "xsd:hexBinary"), new L("@@", "xsd:base64Binary")})
      assertTrue(literal.toString(), LiteralValues.isInvalid(literal));
  }
  @Test public void validBoundsAndSpecialValuesAreAccepted() {
    for (L literal : new L[]{new L("-128", "xsd:byte"), new L("127", "xsd:byte"), new L("18446744073709551615", "xsd:unsignedLong"),
      new L("INF", "xsd:double"), new L("NaN", "xsd:float"), new L("2024-02-29T12:00:00Z", "xsd:dateTimeStamp"),
      new L("unknown", "urn:custom"), new L("chat", "rdf:langString", "fr")})
      assertFalse(literal.toString(), LiteralValues.isInvalid(literal));
  }
  @Test public void equalityUsesValuesAndPreservesStructuralIdentity() {
    L integer = new L("01", "xsd:integer"), decimal = new L("1.0", "xsd:decimal");
    assertFalse(integer.equals(decimal));
    assertTrue(LiteralValues.sameValue(integer, decimal));
    assertTrue(LiteralValues.sameValue(new L("1", "xsd:boolean"), new L("true", "xsd:boolean")));
    assertFalse(LiteralValues.sameValue(new L("ff", "xsd:hexBinary"), new L("/w==", "xsd:base64Binary")));
    assertFalse(LiteralValues.sameValue(new L("chat", "rdf:langString", "en"), new L("chat", "rdf:langString", "fr")));
    assertFalse(LiteralValues.sameValue(new L("256", "xsd:unsignedByte"), new L("256", "xsd:unsignedByte")));
  }
  @Test public void membershipChecksValuesAndSubtypes() {
    assertFalse(LiteralValues.outside(new L("127.00", "xsd:decimal"), "xsd:byte"));
    assertTrue(LiteralValues.outside(new L("127.5", "xsd:decimal"), "xsd:byte"));
    assertTrue(LiteralValues.outside(new L("128", "xsd:integer"), "xsd:byte"));
    assertTrue(LiteralValues.outside(new L("1", "xsd:string"), "xsd:integer"));
    assertFalse(LiteralValues.outside(new L(" 1 ", "xsd:integer"), "xsd:decimal"));
    assertFalse(LiteralValues.outside(new L("value", "urn:custom"), "xsd:integer"));
    assertFalse(LiteralValues.outside(new L("1", "xsd:integer"), "urn:custom"));
  }
  @Test public void dateTimesCompareByValue() {
    assertTrue(LiteralValues.sameValue(new L("2026-01-01T10:00:00+02:00", "xsd:dateTime"), new L("2026-01-01T08:00:00Z", "xsd:dateTimeStamp")));
    assertTrue(LiteralValues.outside(new L("2026-01-01T10:00:00", "xsd:dateTime"), "xsd:dateTimeStamp"));
  }
  @Test public void stringsNamesUrisAndXMLHaveSeparateValueSpaces() {
    assertFalse(LiteralValues.isInvalid(new L("a\u00b7b", "xsd:NCName")));
    assertTrue(LiteralValues.isInvalid(new L("a:b", "xsd:NCName")));
    assertTrue(LiteralValues.isInvalid(new L("<p>", "rdf:XMLLiteral")));
    assertFalse(LiteralValues.isInvalid(new L("<p xmlns='urn:p'>value</p>", "rdf:XMLLiteral")));
    assertTrue(LiteralValues.outside(new L("http://example.org", "xsd:anyURI"), "xsd:string"));
    assertTrue(LiteralValues.outside(new L("ff", "xsd:hexBinary"), "xsd:base64Binary"));
    assertFalse(LiteralValues.sameValue(new L("1", "xsd:float"), new L("1", "xsd:double")));
    assertTrue(LiteralValues.sameValue(new L("text@", "rdf:PlainLiteral"), new L("text", "xsd:string")));
    assertTrue(LiteralValues.sameValue(new L("text@EN", "rdf:PlainLiteral"), new L("text", "rdf:langString", "en")));
  }

}
