package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
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
import org.swrlapi.drools.core.resolvers.DroolsObjectResolver;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DECCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMaxQCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OECCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMaxQCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOCOCE;
import org.swrlapi.drools.owl.classes.OOHSCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts OWLAPI OWL class expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classes.CE
 */
public class DroolsOWLClassExpression2DRLConverter extends DroolsDRLConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
  private final @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter;
  private final @NonNull DroolsObjectResolver droolsObjectResolver;

  public DroolsOWLClassExpression2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsObjectResolver droolsObjectResolver,
    @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter)
  {
    super(bridge);

    this.propertyExpressionConverter = propertyExpressionConverter;
    this.droolsObjectResolver = droolsObjectResolver;

    this.droolsObjectResolver.reset();
  }

  @NonNull public String convert(@NonNull OWLClassExpression classExpression)
  {
    return classExpression.accept(this);
  }

  @NonNull @Override public String convert(@NonNull OWLClass cls)
  {
    String classPrefixedName = iri2PrefixedName(cls.getIRI());

    if (!this.droolsObjectResolver.recordsCEID(classPrefixedName)) {
      C c = new C(classPrefixedName);
      getOWLObjectResolver().recordOWLClass(classPrefixedName, cls);
      this.droolsObjectResolver.recordCE(c);
    }
    return classPrefixedName;
  }

  // TODO Temporary fix - the individual may not be named
  @NonNull @Override public String convert(@NonNull OWLObjectOneOf objectOneOf)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectOneOf)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      for (OWLIndividual individual1 : objectOneOf.getIndividuals()) {
        Set<@NonNull OWLIndividual> individual = new HashSet<>(objectOneOf.getIndividuals());
        String individualID = iri2PrefixedName(individual1.asOWLNamedIndividual().getIRI());

        OOOCE oooce = new OOOCE(classExpressionID, individualID);
        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectOneOf);
        this.droolsObjectResolver.recordCE(oooce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectOneOf);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectIntersectionOf)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      for (OWLClassExpression ce : objectIntersectionOf.getOperands()) {
        String class1ID = convert(ce);
        OIOCE oioce = new OIOCE(classExpressionID, class1ID);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectIntersectionOf);
        this.droolsObjectResolver.recordCE(oioce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectIntersectionOf);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectUnionOf objectUnionOf)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectUnionOf)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      for (OWLClassExpression ce1 : objectUnionOf.getOperands()) {
        String class1ID = convert(ce1);
        OUOCE ouoce = new OUOCE(classExpressionID, class1ID);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectUnionOf);
        this.droolsObjectResolver.recordCE(ouoce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectUnionOf);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectComplementOf)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String complementClassID = convert(objectComplementOf.getOperand());
      OOCOCE oooce = new OOCOCE(classExpressionID, complementClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectComplementOf);
      this.droolsObjectResolver.recordCE(oooce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectComplementOf);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectSomeValuesFrom)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String someValuesFromClassID = convert(objectSomeValuesFrom.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(objectSomeValuesFrom.getProperty());
      OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectSomeValuesFrom);
      this.droolsObjectResolver.recordCE(osvfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectSomeValuesFrom);
  }

  @NonNull @Override public String convert(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataSomeValuesFrom)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String someValuesFromDataRangeID = getDroolsOWLDataRange2IDConverter().convert(dataSomeValuesFrom.getFiller());
      String propertyID = getOWLPropertyExpressionConverter().convert(dataSomeValuesFrom.getProperty());
      DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataSomeValuesFrom);
      this.droolsObjectResolver.recordCE(dsvfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataSomeValuesFrom);
  }

  @NonNull @Override public String convert(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataExactCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(dataExactCardinality.getProperty());
      int cardinality = dataExactCardinality.getCardinality();
      DECCE DECCE = new DECCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataExactCardinality);
      this.droolsObjectResolver.recordCE(DECCE);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataExactCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectExactCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(objectExactCardinality.getProperty());
      int cardinality = objectExactCardinality.getCardinality();
      OECCE OECCE = new OECCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectExactCardinality);
      this.droolsObjectResolver.recordCE(OECCE);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectExactCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataMinCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(dataMinCardinality.getProperty());
      int cardinality = dataMinCardinality.getCardinality();
      DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataMinCardinality);
      this.droolsObjectResolver.recordCE(dmincce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataMinCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectMinCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(objectMinCardinality.getProperty());
      int cardinality = objectMinCardinality.getCardinality();
      OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectMinCardinality);
      this.droolsObjectResolver.recordCE(omincce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectMinCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataMaxCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(dataMaxCardinality.getProperty());
      int cardinality = dataMaxCardinality.getCardinality();

      if (dataMaxCardinality.isQualified()) {
        String fillerID = getDroolsOWLDataRange2IDConverter().convert(dataMaxCardinality.getFiller());
        DMaxQCCE dmaxqcce = new DMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataMaxCardinality);
        this.droolsObjectResolver.recordCE(dmaxqcce);
      } else {
        DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataMaxCardinality);
        this.droolsObjectResolver.recordCE(dmaxcce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataMaxCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectMaxCardinality)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(objectMaxCardinality.getProperty());
      int cardinality = objectMaxCardinality.getCardinality();

      if (objectMaxCardinality.isQualified()) {
        String fillerID = convert(objectMaxCardinality.getFiller());
        OMaxQCCE omaxqcce = new OMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectMaxCardinality);
        this.droolsObjectResolver.recordCE(omaxqcce);
      } else {
        OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);

        getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectMaxCardinality);
        this.droolsObjectResolver.recordCE(omaxcce);
      }
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectMaxCardinality);
  }

  @NonNull @Override public String convert(@NonNull OWLDataHasValue dataHasValue)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataHasValue)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(dataHasValue.getProperty());
      OWLLiteral valueLiteral = dataHasValue.getFiller();
      DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(valueLiteral));

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataHasValue);
      this.droolsObjectResolver.recordCE(dhvce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataHasValue);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasValue objectHasValue)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectHasValue)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(objectHasValue.getProperty());
      String fillerIndividualID = iri2PrefixedName(objectHasValue.getFiller().asOWLNamedIndividual()
        .getIRI()); // TODO Temporary fix - this individual may not be named

      OHVCE ohvce = new OHVCE(classExpressionID, propertyID, fillerIndividualID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectHasValue);
      this.droolsObjectResolver.recordCE(ohvce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectHasValue);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(objectAllValuesFrom)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(objectAllValuesFrom.getProperty());
      String allValuesFromClassID = convert(objectAllValuesFrom.getFiller());
      OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, objectAllValuesFrom);
      this.droolsObjectResolver.recordCE(oavfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(objectAllValuesFrom);
  }

  @NonNull @Override public String convert(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(dataAllValuesFrom)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(dataAllValuesFrom.getProperty());
      String allValuesFromDataRangeID = getDroolsOWLDataRange2IDConverter().convert(dataAllValuesFrom.getFiller());
      DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, dataAllValuesFrom);
      this.droolsObjectResolver.recordCE(davfce);

      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(dataAllValuesFrom);
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasSelf owbjectHasSelf)
  {
    if (!getOWLObjectResolver().recordsOWLClassExpression(owbjectHasSelf)) {
      String classExpressionID = this.droolsObjectResolver.generateCEID();
      String propertyID = getOWLPropertyExpressionConverter().convert(owbjectHasSelf.getProperty());
      OOHSCE oohsce = new OOHSCE(classExpressionID, propertyID);

      getOWLObjectResolver().recordOWLClassExpression(classExpressionID, owbjectHasSelf);
      this.droolsObjectResolver.recordCE(oohsce);
      return classExpressionID;
    } else
      return getOWLObjectResolver().resolveOWLClassExpression2ID(owbjectHasSelf);
  }

  @NonNull public Set<@NonNull CE> getCEs()
  {
    return this.droolsObjectResolver.getCEs();
  }

  @NonNull @Override public String visit(@NonNull OWLClass owlClass)
  {
    return convert(owlClass);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    return convert(objectIntersectionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectUnionOf owlObjectUnionOf)
  {
    return convert(owlObjectUnionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    return convert(objectComplementOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return convert(objectSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return convert(objectAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasValue objectHasValue)
  {
    return convert(objectHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    return convert(objectMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    return convert(objectExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    return convert(objectMaxCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasSelf objectHasSelf)
  {
    return convert(objectHasSelf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectOneOf objectOneOf)
  {
    return convert(objectOneOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return convert(dataSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return convert(dataAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataHasValue dataHasValue)
  {
    return convert(dataHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    return convert(dataMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    return convert(dataExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    return convert(dataMaxCardinality);
  }

  @NonNull private DroolsOWLPropertyExpression2DRLConverter getOWLPropertyExpressionConverter()
  {
    return this.propertyExpressionConverter;
  }
}
