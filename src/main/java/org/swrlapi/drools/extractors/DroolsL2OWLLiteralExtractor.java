package org.swrlapi.drools.extractors;

import java.net.URI;
import java.net.URISyntaxException;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.L;
import org.swrlapi.exceptions.SQWRLLiteralException;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.extractors.TargetRuleEngineOWLLiteralExtractor;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

/**
 * This class converts Drools OWL literals represented by the class {@link L} to their Portability API representation.
 */
public class DroolsL2OWLLiteralExtractor extends TargetRuleEngineExtractorBase implements
		TargetRuleEngineOWLLiteralExtractor<L>
{
	public DroolsL2OWLLiteralExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLLiteral extract(L l) throws TargetRuleEngineException
	{
		try {
			if (l.isString())
				return getOWLLiteralFactory().getOWLLiteral(l.getValue());
			else if (l.isAnyURI()) {
				return getOWLLiteralFactory().getOWLLiteral(new URI(l.getValue()));
			} else if (l.isBoolean())
				return getOWLLiteralFactory().getOWLLiteral(new Boolean(l.getValue()));
			else if (l.isByte())
				return getOWLLiteralFactory().getOWLLiteral(new Byte(l.getValue()));
			else if (l.isShort())
				return getOWLLiteralFactory().getOWLLiteral(new Short(l.getValue()));
			else if (l.isInteger())
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
				IRI IRI = getIRI(l.datatypeName); // TODO Test this
				OWLDatatype datatype = getOWLDatatypeFactory().getOWLDatatype(IRI);
				return getOWLLiteralFactory().getOWLLiteral(l.value, datatype);
			}
		} catch (NumberFormatException e) {
			throw new TargetRuleEngineException("number format exception extracting OWL literal " + l + " with type "
					+ l.getTypeName() + " from Drools", e);
		} catch (URISyntaxException e) {
			throw new TargetRuleEngineException("IRI exception extracting OWL IRI literal " + l + " from Drools", e);
		} catch (SQWRLLiteralException e) {
			throw new TargetRuleEngineException("exception extracting OWL literal " + l + " with type " + l.getTypeName()
					+ " from Drools: " + e.getMessage(), e);
		}
	}
}
