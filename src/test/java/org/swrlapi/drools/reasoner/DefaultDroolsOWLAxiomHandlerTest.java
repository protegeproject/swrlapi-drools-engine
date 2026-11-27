package org.swrlapi.drools.reasoner;

import org.junit.Test;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.CAA;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DefaultDroolsOWLAxiomHandlerTest
{
  @Test public void setEntailmentRequiresEveryAxiomAndAcceptsTheEmptyConjunction()
  {
    DefaultDroolsOWLAxiomHandler handler = new DefaultDroolsOWLAxiomHandler();
    A known = new CAA("A", "i");
    A missing = new CAA("B", "i");
    handler.addAssertOWLAxioms(Collections.singleton(known));
    assertTrue(handler.isEntailed(known));
    assertFalse(handler.isEntailed(missing));
    assertTrue(handler.isEntailed(Collections.singleton(known)));
    assertFalse(handler.isEntailed(new HashSet<>(Arrays.asList(known, missing))));
    assertTrue(handler.isEntailed(Collections.emptySet()));
  }
}
