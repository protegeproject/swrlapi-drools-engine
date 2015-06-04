package org.swrlapi.drools.converters;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLClassExpressionConverter;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.classexpressions.DAVFCE;
import org.swrlapi.drools.owl.classexpressions.DCCE;
import org.swrlapi.drools.owl.classexpressions.DHVCE;
import org.swrlapi.drools.owl.classexpressions.DMaxCCE;
import org.swrlapi.drools.owl.classexpressions.DMaxQCCE;
import org.swrlapi.drools.owl.classexpressions.DMinCCE;
import org.swrlapi.drools.owl.classexpressions.DSVFCE;
import org.swrlapi.drools.owl.classexpressions.OAVFCE;
import org.swrlapi.drools.owl.classexpressions.OCCE;
import org.swrlapi.drools.owl.classexpressions.OHVCE;
import org.swrlapi.drools.owl.classexpressions.OIOCE;
import org.swrlapi.drools.owl.classexpressions.OMaxCCE;
import org.swrlapi.drools.owl.classexpressions.OMaxQCCE;
import org.swrlapi.drools.owl.classexpressions.OMinCCE;
import org.swrlapi.drools.owl.classexpressions.OOCOCE;
import org.swrlapi.drools.owl.classexpressions.OOOCE;
import org.swrlapi.drools.owl.classexpressions.OSVFCE;
import org.swrlapi.drools.owl.classexpressions.OUOCE;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.resolvers.DroolsCEResolver;
import org.swrlapi.drools.resolvers.DroolsResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts OWLAPI OWL class expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classexpressions.CE
 */
public class DroolsOWLClassExpressionConverter extends DroolsConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
  @NonNull private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
  @NonNull private final DroolsResolver resolver;

  public DroolsOWLClassExpressionConverter(@NonNull SWRLRuleEngineBridge bridge, @NonNull DroolsResolver resolver,
    @NonNull DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
  {
    super(bridge);

    this.propertyExpressionConverter = propertyExpressionConverter;
    this.resolver = resolver;

    reset();
  }

  public void reset()
  {
    getOWLClassExpressionResolver().reset();
    getCEResolver().reset();
  }

  @NonNull public String convert(@NonNull OWLClassExpression owlClassExpression)
  {
    return owlClassExpression.accept(this);
  }

  @NonNull @Override public String convert(@NonNull OWLClass cls)
  {
    String classPrefixedName = getIRIResolver().iri2PrefixedName(cls.getIRI());

    if (!getCEResolver().recordsCEID(classPrefixedName)) {
      C c = new C(classPrefixedName);
      getOWLClassExpressionResolver().recordOWLClassExpression(classPrefixedName, cls);
      getCEResolver().record(c);
    }
    return classPrefixedName;
  }

  @NonNull @Override public String convert(@NonNull OWLObjectOneOf classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      for (OWLIndividual individual1 : classExpression.getIndividuals()) {
        Set<OWLIndividual> individuals = new HashSet<>(classExpression.getIndividuals());
        String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);

        individuals.remove(individual1);
        for (OWLIndividual individual2 : individuals) {
          String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
          OOOCE oooce = new OOOCE(classExpressionID, individual1ID, individual2ID);

          getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
          getCEResolver().record(oooce);
        }
      }
      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectIntersectionOf classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      for (OWLClassExpression ce : classExpression.getOperands()) {
        String class1ID = convert(ce);
        OIOCE oioce = new OIOCE(classExpressionID, class1ID);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(oioce);
      }
      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectUnionOf classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      for (OWLClassExpression ce1 : classExpression.getOperands()) {
        String class1ID = convert(ce1);
        OUOCE ouoce = new OUOCE(classExpressionID, class1ID);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(ouoce);
      }
      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectComplementOf classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String complementClassID = convert(classExpression.getOperand());
      OOCOCE oooce = new OOCOCE(classExpressionID, complementClassID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(oooce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectSomeValuesFrom classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String someValuesFromClassID = convert(classExpression.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(osvfce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataSomeValuesFrom classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String someValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(dsvfce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataExactCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      DCCE dcce = new DCCE(classExpressionID, propertyID, cardinality);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(dcce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectExactCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      OCCE occe = new OCCE(classExpressionID, propertyID, cardinality);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(occe);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMinCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(dmincce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMinCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(omincce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMaxCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();

      if (classExpression.isQualified()) {
        String fillerID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
        DMaxQCCE dmaxqcce = new DMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(dmaxqcce);
      } else {
        DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(dmaxcce);
      }
      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMaxCardinality classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();

      if (classExpression.isQualified()) {
        String fillerID = convert(classExpression.getFiller());
        OMaxQCCE omaxqcce = new OMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(omaxqcce);
      } else {
        OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
        getCEResolver().record(omaxcce);
      }
      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataHasValue classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      OWLLiteral valueLiteral = classExpression.getFiller();
      DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(valueLiteral));

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(dhvce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasValue classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String valueIndividualID = getDroolsOWLIndividual2DRLConverter().convert(classExpression.getFiller());
      OHVCE ohvce = new OHVCE(classExpressionID, propertyID, valueIndividualID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(ohvce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectAllValuesFrom classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String allValuesFromClassID = convert(classExpression.getFiller());
      OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(oavfce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataAllValuesFrom classExpression)
  {
    if (!getOWLClassExpressionResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = getCEResolver().generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String allValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
      DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);

      getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
      getCEResolver().record(davfce);

      return classExpressionID;
    } else
      return getOWLClassExpressionResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull public Set<CE> getCEs()
  {
    return getCEResolver().getCEs();
  }

  @NonNull @Override public String visit(@NonNull OWLClass owlClass)
  {
    return convert(owlClass);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectIntersectionOf owlObjectIntersectionOf)
  {
    return convert(owlObjectIntersectionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectUnionOf owlObjectUnionOf)
  {
    return convert(owlObjectUnionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectComplementOf owlObjectComplementOf)
  {
    return convert(owlObjectComplementOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectSomeValuesFrom owlObjectSomeValuesFrom)
  {
    return convert(owlObjectSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectAllValuesFrom owlObjectAllValuesFrom)
  {
    return convert(owlObjectAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasValue owlObjectHasValue)
  {
    return convert(owlObjectHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMinCardinality owlObjectMinCardinality)
  {
    return convert(owlObjectMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectExactCardinality owlObjectExactCardinality)
  {
    return convert(owlObjectExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMaxCardinality owlObjectMaxCardinality)
  {
    return convert(owlObjectMaxCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasSelf owlObjectHasSelf)
  {
    return convert(owlObjectHasSelf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectOneOf owlObjectOneOf)
  {
    return convert(owlObjectOneOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataSomeValuesFrom owlDataSomeValuesFrom)
  {
    return convert(owlDataSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataAllValuesFrom owlDataAllValuesFrom)
  {
    return convert(owlDataAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataHasValue owlDataHasValue)
  {
    return convert(owlDataHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMinCardinality owlDataMinCardinality)
  {
    return convert(owlDataMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataExactCardinality owlDataExactCardinality)
  {
    return convert(owlDataExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMaxCardinality owlDataMaxCardinality)
  {
    return convert(owlDataMaxCardinality);
  }

  @NonNull private DroolsOWLPropertyExpressionConverter getOWLPropertyExpressionConverter()
  {
    return this.propertyExpressionConverter;
  }

  @NonNull private DroolsCEResolver getCEResolver()
  {
    return this.resolver.getDroolsCEResolver();
  }
}
