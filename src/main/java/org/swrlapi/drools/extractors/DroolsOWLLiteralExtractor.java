package org.swrlapi.drools.extractors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.bridge.extractors.TargetRuleEngineOWLLiteralExtractor;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class converts a Drools OWL literal representation to its OWLAPI representation.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see org.swrlapi.drools.owl.core.L
 */
public class DroolsOWLLiteralExtractor extends TargetRuleEngineExtractorBase implements
TargetRuleEngineOWLLiteralExtractor<L>
{
  public DroolsOWLLiteralExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override
  public OWLLiteral extract(@NonNull L l)
  { // TODO See if we can use visitor to get rid of instanceof
    try {
      if (l.isString())
        return getOWLLiteralFactory().getOWLLiteral(l.getValue());
      else if (l.isAnyURI()) {
        return getOWLLiteralFactory().getOWLLiteral(new URI(l.getValue()));
      } else if (l.isBoolean())
        return getOWLLiteralFactory().getOWLLiteral(Boolean.valueOf(l.getValue()));
      else if (l.isByte())
        return getOWLLiteralFactory().getOWLLiteral(new Byte(l.getValue()));
      else if (l.isShort())
        return getOWLLiteralFactory().getOWLLiteral(new Short(l.getValue()));
      else if (l.isInt()) // xsd:int
        return getOWLLiteralFactory().getOWLLiteral(new Integer(l.getValue()));
      else if (l.isLong())
        return getOWLLiteralFactory().getOWLLiteral(new Long(l.getValue()));
      else if (l.isFloat())
        return getOWLLiteralFactory().getOWLLiteral(new Float(l.getValue()));
      else if (l.isDouble())
        return getOWLLiteralFactory().getOWLLiteral(new Double(l.getValue()));
      else if (l.isTime())
        return getOWLLiteralFactory().getOWLLiteral(new XSDTime(l.getValue()));
      else if (l.isDate())
        return getOWLLiteralFactory().getOWLLiteral(new XSDDate(l.getValue()));
      else if (l.isDateTime())
        return getOWLLiteralFactory().getOWLLiteral(new XSDDateTime(l.getValue()));
      else if (l.isDuration())
        return getOWLLiteralFactory().getOWLLiteral(new XSDDuration(l.getValue()));
      else {
        IRI IRI = getIRIResolver().prefixedName2IRI(l.datatypeName);
        OWLDatatype datatype = getOWLDatatypeFactory().getOWLDatatype(IRI);
        return getOWLLiteralFactory().getOWLLiteral(l.value, datatype);
      }
    } catch (NumberFormatException e) {
      throw new TargetSWRLRuleEngineInternalException("number format exception extracting OWL literal " + l
          + " with type " + l.getTypeName() + " from Drools: ", e);
    } catch (URISyntaxException e) {
      throw new TargetSWRLRuleEngineInternalException("IRI exception extracting OWL URI literal " + l
          + " from Drools: ", e);
    } catch (IllegalArgumentException e) {
      throw new TargetSWRLRuleEngineInternalException("exception extracting OWL literal " + l + " with type "
          + l.getTypeName() + " from Drools: " + e.getMessage(), e);
    }
  }
}
