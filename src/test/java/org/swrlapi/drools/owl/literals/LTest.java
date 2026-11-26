package org.swrlapi.drools.owl.literals;

import org.junit.Test;
import org.swrlapi.drools.swrl.BA;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class LTest
{
  @Test public void languageTagsParticipateInLiteralIdentity()
  {
    L french = new L("chat", "rdf:langString", "fr");
    L english = new L("chat", "rdf:langString", "en");
    L uppercaseFrench = new L("chat", "rdf:langString", "FR");

    assertEquals(french, uppercaseFrench);
    assertEquals(french.hashCode(), uppercaseFrench.hashCode());
    assertEquals(2, new HashSet<>(Arrays.asList(french, english, uppercaseFrench)).size());
  }

  @Test public void copiesPreserveLanguageTags()
  {
    L french = new L("bonjour", "rdf:langString", "fr");
    assertEquals(french, new L(french));
    assertEquals(french, new L((BA)french));
    assertEquals("", new L("bonjour", "xsd:string").languageTag);
  }
}
