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
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DCCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMaxQCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OCCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMaxQCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOCOCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.core.resolvers.DroolsObjectResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts OWLAPI OWL class expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classes.CE
 */
public class DroolsOWLClassExpressionConverter extends DroolsConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
  @NonNull private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
  private final @NonNull DroolsObjectResolver expressionResolver;

  public DroolsOWLClassExpressionConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsObjectResolver expressionResolver,
    @NonNull DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
  {
    super(bridge);

    this.propertyExpressionConverter = propertyExpressionConverter;
    this.expressionResolver = expressionResolver;

    this.expressionResolver.reset();
  }

  @NonNull public String convert(@NonNull OWLClassExpression owlClassExpression)
  {
    return owlClassExpression.accept(this);
  }

  @NonNull @Override public String convert(@NonNull OWLClass cls)
  {
    String classPrefixedName = getIRIResolver().iri2PrefixedName(cls.getIRI());

    if (!this.expressionResolver.recordsCEID(classPrefixedName)) {
      C c = new C(classPrefixedName);
      getOWLObjectResolver().recordOWLClassExpression(classPrefixedName, cls);
      this.expressionResolver.recordCE(c);
    }
    return classPrefixedName;
  }

  @NonNull @Override public String convert(@NonNull OWLObjectOneOf classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      for (OWLIndividual individual1 : classExpression.getIndividuals()) {
        Set<OWLIndividual> individuals = new HashSet<>(classExpression.getIndividuals());
        String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);

        individuals.remove(individual1);
        for (OWLIndividual individual2 : individuals) {
          String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
          OOOCE oooce = new OOOCE(classExpressionID, individual1ID, individual2ID);

          getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
          this.expressionResolver.recordCE(oooce);
        }
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectIntersectionOf classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      for (OWLClassExpression ce : classExpression.getOperands()) {
        String class1ID = convert(ce);
        OIOCE oioce = new OIOCE(classExpressionID, class1ID);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(oioce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectUnionOf classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      for (OWLClassExpression ce1 : classExpression.getOperands()) {
        String class1ID = convert(ce1);
        OUOCE ouoce = new OUOCE(classExpressionID, class1ID);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(ouoce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectComplementOf classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String complementClassID = convert(classExpression.getOperand());
      OOCOCE oooce = new OOCOCE(classExpressionID, complementClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(oooce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectSomeValuesFrom classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String someValuesFromClassID = convert(classExpression.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(osvfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataSomeValuesFrom classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String someValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(dsvfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataExactCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      DCCE dcce = new DCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(dcce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectExactCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      OCCE occe = new OCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(occe);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMinCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(dmincce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMinCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();
      OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(omincce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMaxCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();

      if (classExpression.isQualified()) {
        String fillerID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
        DMaxQCCE dmaxqcce = new DMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(dmaxqcce);
      } else {
        DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(dmaxcce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMaxCardinality classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      int cardinality = classExpression.getCardinality();

      if (classExpression.isQualified()) {
        String fillerID = convert(classExpression.getFiller());
        OMaxQCCE omaxqcce = new OMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(omaxqcce);
      } else {
        OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
        this.expressionResolver.recordCE(omaxcce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataHasValue classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      OWLLiteral valueLiteral = classExpression.getFiller();
      DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(valueLiteral));

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(dhvce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasValue classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String valueIndividualID = getDroolsOWLIndividual2DRLConverter().convert(classExpression.getFiller());
      OHVCE ohvce = new OHVCE(classExpressionID, propertyID, valueIndividualID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(ohvce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectAllValuesFrom classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String allValuesFromClassID = convert(classExpression.getFiller());
      OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(oavfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull @Override public String convert(@NonNull OWLDataAllValuesFrom classExpression)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(classExpression)) {
      String classExpressionID = this.expressionResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
      String allValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
      DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, classExpression);
      this.expressionResolver.recordCE(davfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression(classExpression);
  }

  @NonNull public Set<CE> getCEs()
  {
    return this.expressionResolver.getCEs();
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
}
