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

  @Test public void xmlComparisonCanonicalizesContentAndPreservesCommentsAndNamespaces() {
    String[][] equal = {{"<p b='2' a='1'/>", "<p a=\"1\" b=\"2\"></p>"},
      {"<p><![CDATA[a&b]]></p>", "<p>a&amp;b</p>"}, {"", ""},
      {"<p xmlns='urn:p' xmlns:unused='urn:unused'/>", "<p xmlns='urn:p'></p>"},
      {"a<p/>b<q/>", "a<p></p>b<q></q>"}, {"<p>\r\nx</p>", "<p>\nx</p>"}};
    for (String[] pair : equal) {
      L a = new L(pair[0], "rdf:XMLLiteral"), b = new L(pair[1], "rdf:XMLLiteral");
      assertFalse(pair[0], LiteralValues.isInvalid(a));
      assertTrue(pair[0], LiteralValues.sameValue(a, b));
      assertFalse(pair[0], LiteralValues.differentValue(a, b));
    }
    String[][] different = {{"<p/>", "<p><!--comment--></p>"}, {"<p xmlns='urn:a'/>", "<p xmlns='urn:b'/>"},
      {"<p> </p>", "<p/>"}, {"<a/><b/>", "<b/><a/>"},
      {"<a:p xmlns:a='urn:p'/>", "<b:p xmlns:b='urn:p'/>"}};
    for (String[] pair : different) {
      assertFalse(LiteralValues.sameValue(new L(pair[0], "rdf:XMLLiteral"), new L(pair[1], "rdf:XMLLiteral")));
      assertTrue(LiteralValues.differentValue(new L(pair[0], "rdf:XMLLiteral"), new L(pair[1], "rdf:XMLLiteral")));
    }
    assertFalse(LiteralValues.sameValue(new L("text", "rdf:XMLLiteral"), new L("text", "xsd:string")));
    for (String invalid : new String[]{"<p>", "<p:a/>", "<!DOCTYPE p [<!ENTITY ext SYSTEM 'file:///no-such-entity'>]><p>&ext;</p>", "<?xml version='1.0'?><p/>"})
      assertTrue(invalid, LiteralValues.isInvalid(new L(invalid, "rdf:XMLLiteral")));
  }

  @Test public void inequalityRequiresEvidence() {
    L custom = new L("a", "urn:custom");
    assertTrue(LiteralValues.sameValue(custom, new L("a", "urn:custom")));
    assertFalse(LiteralValues.sameValue(custom, new L("b", "urn:custom")));
    assertFalse(LiteralValues.differentValue(custom, new L("b", "urn:custom")));
    assertFalse(LiteralValues.differentValue(custom, new L("1", "xsd:integer")));
    assertFalse(LiteralValues.differentValue(new L("bad", "xsd:int"), new L("2", "xsd:int")));
    L local = new L("2026-01-01T12:00:00", "xsd:dateTime");
    assertFalse(LiteralValues.differentValue(local, new L("2026-01-01T12:00:00Z", "xsd:dateTime")));
    assertFalse(LiteralValues.sameValue(local, new L("2026-01-01T12:00:00Z", "xsd:dateTime")));
    assertTrue(LiteralValues.differentValue(local, new L("2026-01-03T12:00:00Z", "xsd:dateTime")));
  }

  @Test public void floatingPointIdentityFollowsOwlRatherThanArithmeticEquality() {
    for (String type : new String[]{"xsd:float", "xsd:double"}) {
      assertTrue(LiteralValues.differentValue(new L("+0", type), new L("-0", type)));
      assertTrue(LiteralValues.sameValue(new L("NaN", type), new L("NaN", type)));
      assertTrue(LiteralValues.sameValue(new L("1e1000", type), new L("INF", type)));
      assertTrue(LiteralValues.sameValue(new L("1e-1000", type), new L("0", type)));
    }
    assertTrue(LiteralValues.differentValue(new L("1", "xsd:float"), new L("1", "xsd:double")));
    assertTrue(LiteralValues.differentValue(new L("1", "xsd:double"), new L("1", "xsd:decimal")));
    assertTrue(LiteralValues.differentValue(new L("ff", "xsd:hexBinary"), new L("/w==", "xsd:base64Binary")));
  }

  @Test public void positiveMembershipDoesNotTurnUnknownIntoTrue() {
    assertTrue(LiteralValues.inside(new L("1.0", "xsd:decimal"), "xsd:integer"));
    assertFalse(LiteralValues.inside(new L("1.5", "xsd:decimal"), "xsd:integer"));
    assertFalse(LiteralValues.inside(new L("1", "xsd:int"), "urn:custom"));
    assertFalse(LiteralValues.inside(new L("1", "urn:custom"), "xsd:int"));
    assertFalse(LiteralValues.inside(new L("invalid", "xsd:int"), "rdfs:Literal"));
    assertTrue(LiteralValues.inside(new L("custom", "urn:custom"), "rdfs:Literal"));
  }

  @Test public void onlyXmlWhitespaceIsCollapsed() {
    assertTrue(LiteralValues.sameValue(new L(" \t01\r\n", "xsd:int"), new L("1", "xsd:integer")));
    assertTrue(LiteralValues.isInvalid(new L("\u000b1", "xsd:int")));
    assertTrue(LiteralValues.isInvalid(new L("1\u0000", "xsd:int")));
    assertTrue(LiteralValues.sameValue(new L(" a\t b\n", "xsd:token"), new L("a b", "xsd:string")));
    assertFalse(LiteralValues.sameValue(new L("\u000ba", "xsd:token"), new L("a", "xsd:string")));
  }

  @Test public void languageTagsRequireWellFormedBcp47Syntax() {
    for (String tag : new String[]{"en", "de-CH-1996", "zh-Hant", "x-private", "i-klingon"})
      assertFalse(tag, LiteralValues.isInvalid(new L("text", "rdf:langString", tag)));
    for (String tag : new String[]{"", "en_US", "en-abcdefghi", "en-u", "x"})
      assertTrue(tag, LiteralValues.isInvalid(new L("text", "rdf:langString", tag)));
  }
}
