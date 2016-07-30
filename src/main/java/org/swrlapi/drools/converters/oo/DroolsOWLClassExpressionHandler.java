package org.swrlapi.drools.converters.oo;

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
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLClassExpressionConverter;
import org.swrlapi.drools.converters.id.DroolsOWLDataRangeHandler;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.CEConverter;
import org.swrlapi.drools.owl.classes.CEResolver;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DECCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OCOCE;
import org.swrlapi.drools.owl.classes.OECCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOHSCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DroolsOWLClassExpressionHandler extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<CE>, OWLClassExpressionVisitorEx<CE>, CEConverter, CEResolver
{
  @NonNull private final DroolsOWLIndividual2IConverter droolsOWLIndividual2IConverter;
  @NonNull private final DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler;
  @NonNull private final DroolsOWLDataRangeHandler droolsOWLDataRangeHandler;
  @NonNull private final DroolsOWLLiteral2LConverter droolsOWLLiteral2LConverter;

  @NonNull private final Map<@NonNull OWLClass, @NonNull C> ce2c = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectIntersectionOf, @NonNull OIOCE> ce2oioce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectUnionOf, @NonNull OUOCE> ce2ouoce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectComplementOf, @NonNull OCOCE> ce2ococe = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectSomeValuesFrom, @NonNull OSVFCE> ce2osvfce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectAllValuesFrom, @NonNull OAVFCE> ce2oavfce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectHasValue, @NonNull OHVCE> ce2ohvce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectExactCardinality, @NonNull OECCE> ce2oecce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectMinCardinality, @NonNull OMinCCE> ce2omincce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectMaxCardinality, @NonNull OMaxCCE> ce2omaxcce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectHasSelf, @NonNull OOHSCE> ce2oohsce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLObjectOneOf, @NonNull OOOCE> ce2oooce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataSomeValuesFrom, @NonNull DSVFCE> ce2dsvfce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataAllValuesFrom, @NonNull DAVFCE> ce2davfce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataHasValue, @NonNull DHVCE> ce2dhvce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataExactCardinality, @NonNull DECCE> ce2decce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataMinCardinality, @NonNull DMinCCE> ce2dmincce = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataMaxCardinality, @NonNull DMaxCCE> ce2dmaxcce = new HashMap<>();

  @NonNull private final Map<@NonNull String, @NonNull OWLClass> c2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectIntersectionOf> oioce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectUnionOf> ouoce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectComplementOf> ococe2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectSomeValuesFrom> osvfce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectAllValuesFrom> oavfce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectHasValue> ohvce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectExactCardinality> oecce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectMinCardinality> omincce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectMaxCardinality> omaxcce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectHasSelf> oohsce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectOneOf> oooce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataSomeValuesFrom> dsvfce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataAllValuesFrom> davfce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataHasValue> dhvce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataExactCardinality> decce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataMinCardinality> dmincce2ce = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataMaxCardinality> dmaxcce2ce = new HashMap<>();

  @NonNull private final Map<@NonNull String, @NonNull CE> ces = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLClassExpression> classExpressions = new HashMap<>();

  private int classExpressionIndex = 0;

  public DroolsOWLClassExpressionHandler(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLIndividual2IConverter droolsOWLIndividual2IConverter,
    @NonNull DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler,
    @NonNull DroolsOWLDataRangeHandler droolsOWLDataRangeHandler,
    @NonNull DroolsOWLLiteral2LConverter droolsOWLLiteral2LConverter)
  {
    super(bridge);
    this.droolsOWLIndividual2IConverter = droolsOWLIndividual2IConverter;
    this.droolsOWLPropertyExpressionHandler = droolsOWLPropertyExpressionHandler;
    this.droolsOWLDataRangeHandler = droolsOWLDataRangeHandler;
    this.droolsOWLLiteral2LConverter = droolsOWLLiteral2LConverter;

    convert(getBridge().getSWRLAPIOWLDataFactory().getOWLThing()); // TODO Quick hack to make an owl:Thing
    convert(getBridge().getSWRLAPIOWLDataFactory().getOWLNothing()); // TODO Quick hack to make an owl:Nothing
  }

  @NonNull @Override public CE convert(@NonNull OWLClassExpression classExpression)
  {
    return classExpression.accept(this);
  }

  public void reset()
  {
    this.classExpressionIndex = 0;

    this.ce2c.clear();
    this.ce2oioce.clear();
    this.ce2ouoce.clear();
    this.ce2ococe.clear();
    this.ce2osvfce.clear();
    this.ce2oavfce.clear();
    this.ce2ohvce.clear();
    this.ce2oecce.clear();
    this.ce2omincce.clear();
    this.ce2omaxcce.clear();
    this.ce2oohsce.clear();
    this.ce2oooce.clear();
    this.ce2dsvfce.clear();
    this.ce2davfce.clear();
    this.ce2dhvce.clear();
    this.ce2decce.clear();
    this.ce2dmincce.clear();
    this.ce2dmaxcce.clear();

    this.c2ce.clear();
    this.oioce2ce.clear();
    this.ouoce2ce.clear();
    this.ococe2ce.clear();
    this.osvfce2ce.clear();
    this.oavfce2ce.clear();
    this.ohvce2ce.clear();
    this.oecce2ce.clear();
    this.omincce2ce.clear();
    this.omaxcce2ce.clear();
    this.oohsce2ce.clear();
    this.oooce2ce.clear();
    this.dsvfce2ce.clear();
    this.davfce2ce.clear();
    this.dhvce2ce.clear();
    this.decce2ce.clear();
    this.dmincce2ce.clear();
    this.dmaxcce2ce.clear();

    this.ces.clear();
    this.classExpressions.clear();

    convert(getBridge().getSWRLAPIOWLDataFactory().getOWLThing()); // TODO Quick hack to make an owl:Thing
    convert(getBridge().getSWRLAPIOWLDataFactory().getOWLNothing()); // TODO Quick hack to make an owl:Nothing
  }

  @NonNull @Override public C visit(@NonNull OWLClass cls)
  {
    if (ce2c.containsKey(cls))
      return ce2c.get(cls);
    else {
      String classID = iri2PrefixedName(cls.getIRI());
      C c = new C(classID);
      ce2c.put(cls, c);
      c2ce.put(c.getceid(), cls);
      ces.put(classID, c);
      classExpressions.put(classID, cls);

      return c;
    }
  }

  @NonNull @Override public OIOCE visit(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    if (ce2oioce.containsKey(objectIntersectionOf))
      return ce2oioce.get(objectIntersectionOf);
    else {
      String classExpressionID = generateCEID();
      Set<String> classIDs = new HashSet<>();
      for (OWLClassExpression ce : objectIntersectionOf.getOperands()) {
        String classID = convert(ce).getceid();
        classIDs.add(classID);
      }
      OIOCE oioce = new OIOCE(classExpressionID, classIDs);
      ce2oioce.put(objectIntersectionOf, oioce);
      oioce2ce.put(oioce.getceid(), objectIntersectionOf);
      ces.put(classExpressionID, oioce);
      classExpressions.put(classExpressionID, objectIntersectionOf);

      return oioce;
    }
  }

  @NonNull @Override public OUOCE visit(@NonNull OWLObjectUnionOf objectUnionOf)
  {
    if (ce2ouoce.containsKey(objectUnionOf))
      return ce2ouoce.get(objectUnionOf);
    else {
      String classExpressionID = generateCEID();

      Set<@NonNull String> classExpressionIDs = new HashSet<>();
      for (OWLClassExpression ce : objectUnionOf.getOperands()) {
        String cid = convert(ce).getceid();
        classExpressionIDs.add(cid);
      }
      OUOCE ouoce = new OUOCE(classExpressionID, classExpressionIDs);
      ce2ouoce.put(objectUnionOf, ouoce);
      ouoce2ce.put(ouoce.getceid(), objectUnionOf);
      ces.put(classExpressionID, ouoce);
      classExpressions.put(classExpressionID, objectUnionOf);

      return ouoce;
    }
  }

  @NonNull @Override public OCOCE visit(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    if (ce2ococe.containsKey(objectComplementOf))
      return ce2ococe.get(objectComplementOf);
    else {
      String classExpressionID = generateCEID();
      String complementClassID = convert(objectComplementOf.getOperand()).getceid();
      OCOCE ococe = new OCOCE(classExpressionID, complementClassID);
      ce2ococe.put(objectComplementOf, ococe);
      ococe2ce.put(ococe.getceid(), objectComplementOf);
      ces.put(classExpressionID, ococe);
      classExpressions.put(classExpressionID, objectComplementOf);

      return ococe;
    }
  }

  @NonNull @Override public OSVFCE visit(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    if (ce2osvfce.containsKey(objectSomeValuesFrom))
      return ce2osvfce.get(objectSomeValuesFrom);
    else {
      String classExpressionID = generateCEID();
      String someValuesFromClassID = convert(objectSomeValuesFrom.getFiller()).getceid();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectSomeValuesFrom.getProperty()).getid();
      OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);
      ce2osvfce.put(objectSomeValuesFrom, osvfce);
      osvfce2ce.put(osvfce.getceid(), objectSomeValuesFrom);
      ces.put(classExpressionID, osvfce);
      classExpressions.put(classExpressionID, objectSomeValuesFrom);

      return osvfce;
    }
  }

  @NonNull @Override public OAVFCE visit(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    if (ce2oavfce.containsKey(objectAllValuesFrom))
      return ce2oavfce.get(objectAllValuesFrom);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectAllValuesFrom.getProperty()).getid();
      String allValuesFromClassID = convert(objectAllValuesFrom.getFiller()).getceid();
      OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);
      ce2oavfce.put(objectAllValuesFrom, oavfce);
      oavfce2ce.put(oavfce.getceid(), objectAllValuesFrom);
      ces.put(classExpressionID, oavfce);
      classExpressions.put(classExpressionID, objectAllValuesFrom);

      return oavfce;
    }
  }

  @NonNull @Override public OHVCE visit(@NonNull OWLObjectHasValue objectHasValue)
  {
    if (ce2ohvce.containsKey(objectHasValue))
      return ce2ohvce.get(objectHasValue);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectHasValue.getProperty()).getid();
      String fillerIndividualID = getDroolsOWLIndividual2IConverter().convert(objectHasValue.getFiller()).getid();
      OHVCE ohvce = new OHVCE(classExpressionID, propertyID, fillerIndividualID);
      ce2ohvce.put(objectHasValue, ohvce);
      ohvce2ce.put(ohvce.getceid(), objectHasValue);
      ces.put(classExpressionID, ohvce);
      classExpressions.put(classExpressionID, objectHasValue);

      return ohvce;
    }
  }

  @NonNull @Override public OECCE visit(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    if (ce2oecce.containsKey(objectExactCardinality))
      return ce2oecce.get(objectExactCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectExactCardinality.getProperty()).getid();
      int cardinality = objectExactCardinality.getCardinality();
      OECCE oecce = new OECCE(classExpressionID, propertyID, cardinality);
      ce2oecce.put(objectExactCardinality, oecce);
      oecce2ce.put(oecce.getceid(), objectExactCardinality);
      ces.put(classExpressionID, oecce);
      classExpressions.put(classExpressionID, objectExactCardinality);

      return oecce;
    }
  }

  @NonNull @Override public OMinCCE visit(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    if (ce2omincce.containsKey(objectMinCardinality))
      return ce2omincce.get(objectMinCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectMinCardinality.getProperty()).getid();
      int cardinality = objectMinCardinality.getCardinality();
      OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);
      ce2omincce.put(objectMinCardinality, omincce);
      omincce2ce.put(omincce.getceid(), objectMinCardinality);
      ces.put(classExpressionID, omincce);
      classExpressions.put(classExpressionID, objectMinCardinality);

      return omincce;
    }
  }

  @NonNull @Override public OMaxCCE visit(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    if (ce2omaxcce.containsKey(objectMaxCardinality))
      return ce2omaxcce.get(objectMaxCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectMaxCardinality.getProperty()).getid();
      int cardinality = objectMaxCardinality.getCardinality();
      OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);
      ce2omaxcce.put(objectMaxCardinality, omaxcce);
      omaxcce2ce.put(omaxcce.getceid(), objectMaxCardinality);
      ces.put(classExpressionID, omaxcce);
      classExpressions.put(classExpressionID, objectMaxCardinality);

      return omaxcce;
    }
  }

  @NonNull @Override public OOHSCE visit(@NonNull OWLObjectHasSelf objectHasSelf)
  {
    if (ce2oohsce.containsKey(objectHasSelf))
      return ce2oohsce.get(objectHasSelf);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(objectHasSelf.getProperty()).getid();
      OOHSCE oohsce = new OOHSCE(classExpressionID, propertyID);
      ce2oohsce.put(objectHasSelf, oohsce);
      oohsce2ce.put(oohsce.getceid(), objectHasSelf);
      ces.put(classExpressionID, oohsce);
      classExpressions.put(classExpressionID, objectHasSelf);

      return oohsce;
    }
  }

  @NonNull @Override public OOOCE visit(@NonNull OWLObjectOneOf objectOneOf)
  {
    if (ce2oooce.containsKey(objectOneOf))
      return ce2oooce.get(objectOneOf);
    else {
      String classExpressionID = generateCEID();
      Set<@NonNull String> individualIDs = new HashSet<>();
      for (OWLIndividual individual : objectOneOf.getIndividuals()) {
        String individualID = getDroolsOWLIndividual2IConverter().convert(individual).getid();
        individualIDs.add(individualID);
      }
      OOOCE oooce = new OOOCE(classExpressionID, individualIDs);
      ce2oooce.put(objectOneOf, oooce);
      oooce2ce.put(oooce.getceid(), objectOneOf);
      ces.put(classExpressionID, oooce);
      classExpressions.put(classExpressionID, objectOneOf);

      return oooce;
    }
  }

  @NonNull @Override public DSVFCE visit(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    if (ce2dsvfce.containsKey(dataSomeValuesFrom))
      return ce2dsvfce.get(dataSomeValuesFrom);
    else {
      String classExpressionID = generateCEID();
      String someValuesFromDataRangeID = getDroolsOWLDataRangeHandler().convert(dataSomeValuesFrom.getFiller());
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataSomeValuesFrom.getProperty()).getid();
      DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);
      ce2dsvfce.put(dataSomeValuesFrom, dsvfce);
      dsvfce2ce.put(dsvfce.getceid(), dataSomeValuesFrom);
      ces.put(classExpressionID, dsvfce);
      classExpressions.put(classExpressionID, dataSomeValuesFrom);

      return dsvfce;
    }
  }

  @NonNull @Override public DAVFCE visit(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    if (ce2davfce.containsKey(dataAllValuesFrom))
      return ce2davfce.get(dataAllValuesFrom);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataAllValuesFrom.getProperty()).getid();
      String allValuesFromDataRangeID = getDroolsOWLDataRangeHandler().convert(dataAllValuesFrom.getFiller());
      DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);
      ce2davfce.put(dataAllValuesFrom, davfce);
      davfce2ce.put(davfce.getceid(), dataAllValuesFrom);
      ces.put(classExpressionID, davfce);
      classExpressions.put(classExpressionID, dataAllValuesFrom);

      return davfce;
    }
  }

  @NonNull @Override public DHVCE visit(@NonNull OWLDataHasValue dataHasValue)
  {
    if (ce2dhvce.containsKey(dataHasValue))
      return ce2dhvce.get(dataHasValue);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataHasValue.getProperty()).getid();
      OWLLiteral fillerLiteral = dataHasValue.getFiller();
      DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(fillerLiteral));
      ce2dhvce.put(dataHasValue, dhvce);
      dhvce2ce.put(dhvce.getceid(), dataHasValue);
      ces.put(classExpressionID, dhvce);
      classExpressions.put(classExpressionID, dataHasValue);

      return dhvce;
    }
  }

  @NonNull @Override public DMinCCE visit(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    if (ce2dmincce.containsKey(dataMinCardinality))
      return ce2dmincce.get(dataMinCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataMinCardinality.getProperty()).getid();
      int cardinality = dataMinCardinality.getCardinality();
      DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);
      ce2dmincce.put(dataMinCardinality, dmincce);
      dmincce2ce.put(dmincce.getceid(), dataMinCardinality);
      ces.put(classExpressionID, dmincce);
      classExpressions.put(classExpressionID, dataMinCardinality);

      return dmincce;
    }
  }

  @NonNull @Override public DECCE visit(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    if (ce2decce.containsKey(dataExactCardinality))
      return ce2decce.get(dataExactCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataExactCardinality.getProperty()).getid();
      int cardinality = dataExactCardinality.getCardinality();
      DECCE decce = new DECCE(classExpressionID, propertyID, cardinality);
      ce2decce.put(dataExactCardinality, decce);
      decce2ce.put(decce.getceid(), dataExactCardinality);
      ces.put(classExpressionID, decce);
      classExpressions.put(classExpressionID, dataExactCardinality);

      return decce;
    }
  }

  @NonNull @Override public DMaxCCE visit(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    if (ce2dmaxcce.containsKey(dataMaxCardinality))
      return ce2dmaxcce.get(dataMaxCardinality);
    else {
      String classExpressionID = generateCEID();
      String propertyID = getDroolsOWLPropertyExpressionHandler().convert(dataMaxCardinality.getProperty()).getid();
      int cardinality = dataMaxCardinality.getCardinality();
      DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);
      ce2dmaxcce.put(dataMaxCardinality, dmaxcce);
      dmaxcce2ce.put(dmaxcce.getceid(), dataMaxCardinality);
      ces.put(classExpressionID, dmaxcce);
      classExpressions.put(classExpressionID, dataMaxCardinality);

      return dmaxcce;
    }
  }

  @NonNull @Override public C convert(OWLClass cls)
  {
    return visit(cls);
  }

  @NonNull @Override public OOOCE convert(OWLObjectOneOf objectOneOf)
  {
    return visit(objectOneOf);
  }

  @NonNull @Override public OIOCE convert(OWLObjectIntersectionOf objectIntersectionOf)
  {
    return visit(objectIntersectionOf);
  }

  @NonNull @Override public OUOCE convert(OWLObjectUnionOf objectUnionOf)
  {
    return visit(objectUnionOf);
  }

  @NonNull @Override public OSVFCE convert(OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return visit(objectSomeValuesFrom);
  }

  @NonNull @Override public OCOCE convert(OWLObjectComplementOf objectComplementOf)
  {
    return visit(objectComplementOf);
  }

  @NonNull @Override public DSVFCE convert(OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return visit(dataSomeValuesFrom);
  }

  @NonNull @Override public DECCE convert(OWLDataExactCardinality dataExactCardinality)
  {
    return visit(dataExactCardinality);
  }

  @NonNull @Override public OECCE convert(OWLObjectExactCardinality objectExactCardinality)
  {
    return visit(objectExactCardinality);
  }

  @NonNull @Override public DMinCCE convert(OWLDataMinCardinality dataMinCardinality)
  {
    return visit(dataMinCardinality);
  }

  @NonNull @Override public OMinCCE convert(OWLObjectMinCardinality objectMinCardinality)
  {
    return visit(objectMinCardinality);
  }

  @NonNull @Override public DMaxCCE convert(OWLDataMaxCardinality dataMaxCardinality)
  {
    return visit(dataMaxCardinality);
  }

  @NonNull @Override public OMaxCCE convert(OWLObjectMaxCardinality objectMaxCardinality)
  {
    return visit(objectMaxCardinality);
  }

  @NonNull @Override public DHVCE convert(OWLDataHasValue dataHasValue)
  {
    return visit(dataHasValue);
  }

  @NonNull @Override public OHVCE convert(OWLObjectHasValue objectHasValue)
  {
    return visit(objectHasValue);
  }

  @NonNull @Override public OAVFCE convert(OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return visit(objectAllValuesFrom);
  }

  @NonNull @Override public DAVFCE convert(OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return visit(dataAllValuesFrom);
  }

  @NonNull @Override public OOHSCE convert(OWLObjectHasSelf objectHasSelf)
  {
    return visit(objectHasSelf);
  }

  @NonNull @Override public OWLClass convert(@NonNull C c)
  {
    return resolveOWLClass(c.getceid());
  }

  @NonNull @Override public OWLDataAllValuesFrom convert(@NonNull DAVFCE ce)
  {
    return resolveOWLDataAllValuesFrom(ce.getceid());
  }

  @NonNull @Override public OWLDataExactCardinality convert(@NonNull DECCE ce)
  {
    return resolveOWLDataExactCardinality(ce.getceid());
  }

  @NonNull @Override public OWLDataHasValue convert(@NonNull DHVCE ce)
  {
    return resolveOWLDataHasValue(ce.getceid());
  }

  @NonNull @Override public OWLDataMaxCardinality convert(@NonNull DMaxCCE ce)
  {
    return resolveOWLDataMaxCardinality(ce.getceid());
  }

  @NonNull @Override public OWLDataMinCardinality convert(@NonNull DMinCCE ce)
  {
    return resolveOWLDataMinCardinality(ce.getceid());
  }

  @NonNull @Override public OWLDataSomeValuesFrom convert(@NonNull DSVFCE ce)
  {
    return resolveOWLDataSomeValuesFrom(ce.getceid());
  }

  @NonNull @Override public OWLObjectAllValuesFrom convert(@NonNull OAVFCE ce)
  {
    return resolveOWLObjectAllValuesFrom(ce.getceid());
  }

  @NonNull @Override public OWLObjectHasValue convert(@NonNull OHVCE ce)
  {
    return resolveOWLObjectHasValue(ce.getceid());
  }

  @NonNull @Override public OWLObjectIntersectionOf convert(@NonNull OIOCE ce)
  {
    return resolveOWLObjectIntersectionOf(ce.getceid());
  }

  @NonNull @Override public OWLObjectMaxCardinality convert(@NonNull OMaxCCE ce)
  {
    return resolveOWLObjectMaxCardinality(ce.getceid());
  }

  @NonNull @Override public OWLObjectMinCardinality convert(@NonNull OMinCCE ce)
  {
    return resolveOWLObjectMinCardinality(ce.getceid());
  }

  @NonNull @Override public OWLObjectExactCardinality convert(@NonNull OECCE ce)
  {
    return resolveOWLObjectExactCardinality(ce.getceid());
  }

  @NonNull @Override public OWLObjectSomeValuesFrom convert(@NonNull OSVFCE ce)
  {
    return resolveOWLObjectSomeValuesFrom(ce.getceid());
  }

  @NonNull @Override public OWLObjectUnionOf convert(@NonNull OUOCE ce)
  {
    return resolveOWLObjectUnionOf(ce.getceid());
  }

  @NonNull @Override public OWLClassExpression resolveOWLClassExpression(@NonNull String ceid)
  {
    if (ces.containsKey(ceid))
      return classExpressions.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL class expression from a Drools class expression with id " + ceid);
  }

  @NonNull @Override public OWLClass resolveOWLClass(@NonNull String ceid)
  {
    if (c2ce.containsKey(ceid))
      return c2ce.get(ceid);
    else
      throw new IllegalArgumentException("could not resolve an OWL class from a Drools class with id " + ceid);
  }

  @NonNull @Override public OWLDataAllValuesFrom resolveOWLDataAllValuesFrom(@NonNull String ceid)
  {
    if (davfce2ce.containsKey(ceid))
      return davfce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data all values from class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLDataExactCardinality resolveOWLDataExactCardinality(@NonNull String ceid)
  {
    if (decce2ce.containsKey(ceid))
      return decce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data exact cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLDataHasValue resolveOWLDataHasValue(@NonNull String ceid)
  {
    if (dhvce2ce.containsKey(ceid))
      return dhvce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data has value class expression from a Drools class expression with id " + ceid);
  }

  @NonNull @Override public OWLDataMaxCardinality resolveOWLDataMaxCardinality(@NonNull String ceid)
  {
    if (dmaxcce2ce.containsKey(ceid))
      return dmaxcce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data max cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLDataMinCardinality resolveOWLDataMinCardinality(@NonNull String ceid)
  {
    if (dmincce2ce.containsKey(ceid))
      return dmincce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data min cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLDataSomeValuesFrom resolveOWLDataSomeValuesFrom(@NonNull String ceid)
  {
    if (dsvfce2ce.containsKey(ceid))
      return dsvfce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data some values from class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectAllValuesFrom resolveOWLObjectAllValuesFrom(@NonNull String ceid)
  {
    if (oavfce2ce.containsKey(ceid))
      return oavfce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object all values from class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectExactCardinality resolveOWLObjectExactCardinality(@NonNull String ceid)
  {
    if (oecce2ce.containsKey(ceid))
      return oecce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object exact cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectHasValue resolveOWLObjectHasValue(@NonNull String ceid)
  {
    if (ohvce2ce.containsKey(ceid))
      return ohvce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object has value class expression from a Drools class expression with id " + ceid);
  }

  @NonNull @Override public OWLObjectIntersectionOf resolveOWLObjectIntersectionOf(@NonNull String ceid)
  {
    if (oioce2ce.containsKey(ceid))
      return oioce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object intersection of class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectMaxCardinality resolveOWLObjectMaxCardinality(@NonNull String ceid)
  {
    if (omaxcce2ce.containsKey(ceid))
      return omaxcce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object max cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectMinCardinality resolveOWLObjectMinCardinality(@NonNull String ceid)
  {
    if (omincce2ce.containsKey(ceid))
      return omincce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object min cardinality class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectSomeValuesFrom resolveOWLObjectSomeValuesFrom(@NonNull String ceid)
  {
    if (osvfce2ce.containsKey(ceid))
      return osvfce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object some values from class expression from a Drools class expression with id "
          + ceid);
  }

  @NonNull @Override public OWLObjectUnionOf resolveOWLObjectUnionOf(@NonNull String ceid)
  {
    if (ouoce2ce.containsKey(ceid))
      return ouoce2ce.get(ceid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object union of class expression from a Drools class expression with id " + ceid);
  }

  @NonNull public Set<@NonNull CE> getCEs()
  {
    return new HashSet<>(this.ces.values());
  }

  @NonNull private String generateCEID()
  {
    return "CEID" + this.classExpressionIndex++;
  }

  @NonNull private DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
  {
    return this.droolsOWLIndividual2IConverter;
  }

  private @NonNull DroolsOWLPropertyExpressionHandler getDroolsOWLPropertyExpressionHandler()
  {
    return this.droolsOWLPropertyExpressionHandler;
  }

  @NonNull private DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
  {
    return this.droolsOWLLiteral2LConverter;
  }

  private @NonNull DroolsOWLDataRangeHandler getDroolsOWLDataRangeHandler()
  {
    return this.droolsOWLDataRangeHandler;
  }

}
