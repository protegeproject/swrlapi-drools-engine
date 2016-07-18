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
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DECCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OECCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOCOCE;
import org.swrlapi.drools.owl.classes.OOHSCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;

import java.util.HashMap;
import java.util.Map;

class DroolsOWLClassExpression2CEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<CE>, OWLClassExpressionVisitorEx<CE>
{
  private final Map<OWLObjectIntersectionOf, OIOCE> oioces = new HashMap<>();
  private final Map<OWLObjectUnionOf, OUOCE> ouoces = new HashMap<>();
  private final Map<OWLObjectComplementOf, OOCOCE> occoces = new HashMap<>();
  private final Map<OWLObjectSomeValuesFrom, OSVFCE> osvfces = new HashMap<>();
  private final Map<OWLObjectAllValuesFrom, OAVFCE> oavfces = new HashMap<>();
  private final Map<OWLObjectHasValue, OHVCE> ohvces = new HashMap<>();
  private final Map<OWLObjectExactCardinality, OECCE> occes = new HashMap<>();
  private final Map<OWLObjectMinCardinality, OMinCCE> omincces = new HashMap<>();
  private final Map<OWLObjectMaxCardinality, OMaxCCE> omaxcces = new HashMap<>();
  private final Map<OWLObjectHasSelf, OOHSCE> oohsces = new HashMap<>();
  private final Map<OWLObjectOneOf, OOOCE> oooces = new HashMap<>();
  private final Map<OWLDataSomeValuesFrom, DSVFCE> dsvfces = new HashMap<>();
  private final Map<OWLDataAllValuesFrom, DAVFCE> davfces = new HashMap<>();
  private final Map<OWLDataHasValue, DHVCE> dhvces = new HashMap<>();
  private final Map<OWLDataExactCardinality, DECCE> decces = new HashMap<>();
  private final Map<OWLDataMinCardinality, DMinCCE> dmincces = new HashMap<>();
  private final Map<OWLDataMaxCardinality, DMaxCCE> dmaxcces = new HashMap<>();

  private int classExpressionIndex = 0;

  public DroolsOWLClassExpression2CEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public CE convert(@NonNull OWLClassExpression classExpression)
  {
    return classExpression.accept(this);
  }

  public void reset()
  {
    this.classExpressionIndex = 0;

    this.oioces.clear();
    this.ouoces.clear();
    this.occoces.clear();
    this.osvfces.clear();
    this.oavfces.clear();
    this.ohvces.clear();
    this.occes.clear();
    this.omincces.clear();
    this.omaxcces.clear();
    this.oohsces.clear();
    this.oooces.clear();
    this.dsvfces.clear();
    this.davfces.clear();
    this.dhvces.clear();
    this.decces.clear();
    this.dmincces.clear();
    this.dmaxcces.clear();
  }

  @NonNull @Override public C visit(@NonNull OWLClass cls)
  {
    String classPrefixedName = iri2PrefixedName(cls.getIRI());

    return new C(classPrefixedName);
  }

  @NonNull @Override public OIOCE visit(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    throw new RuntimeException("create OIOCE");
  }

  @NonNull @Override public OUOCE visit(@NonNull OWLObjectUnionOf objectUnionOf)
  {
    throw new RuntimeException("create OUOCE");
  }

  @Override public @NonNull OOCOCE visit(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    throw new RuntimeException("create OCCO");
  }

  @NonNull @Override public OSVFCE visit(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    throw new RuntimeException("create OSVFCE");
  }

  @NonNull @Override public OAVFCE visit(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    throw new RuntimeException("create OAFVCE");
  }

  @NonNull @Override public OHVCE visit(@NonNull OWLObjectHasValue objectHasValue)
  {
    throw new RuntimeException("create OHVCE");
  }

  @Override public @NonNull OECCE visit(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    throw new RuntimeException("create OECCE");
  }

  @NonNull @Override public OMinCCE visit(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    throw new RuntimeException("create OMinCCE");
  }

  @NonNull @Override public OMaxCCE visit(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    throw new RuntimeException("create OMaxCCE");
  }

  @Override public @NonNull OOHSCE visit(@NonNull OWLObjectHasSelf objectHasSelf)
  {
    throw new RuntimeException("create OOHSCE");
  }

  @NonNull @Override public OOOCE visit(@NonNull OWLObjectOneOf objectOneOf)
  {
    throw new RuntimeException("create OOOCE");
    //    if (oooces.containsKey(objectOneOf))
    //      return oooces.get(objectOneOf);
    //    else {
    //      String classExpressionID = generateCEID();
    //      for (OWLIndividual individual : objectOneOf.getIndividuals()) {
    //        Set<@NonNull OWLIndividual> individuals = new HashSet<>(objectOneOf.getIndividuals());
    //        String individualID = iri2PrefixedName(individual1.asOWLNamedIndividual().getIRI());
    //
    //        OOOCE oooce = new OOOCE(classExpressionID, individual1ID);
    //
    //      }
    //    }
  }

  @NonNull @Override public DSVFCE visit(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    throw new RuntimeException("create DSVFCE");
  }

  @NonNull @Override public DAVFCE visit(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    throw new RuntimeException("create DAVFCE");
  }

  @NonNull @Override public DHVCE visit(@NonNull OWLDataHasValue dataHasValue)
  {
    throw new RuntimeException("create DHVCE");
  }

  @NonNull @Override public DMinCCE visit(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    throw new RuntimeException("create DMinCCE");
  }

  @Override public @NonNull DECCE visit(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    throw new RuntimeException("create DCCE");
  }

  @NonNull @Override public DMaxCCE visit(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    throw new RuntimeException("create DMaxCCE");
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

  @Override public @NonNull OOCOCE convert(OWLObjectComplementOf objectComplementOf)
  {
    return visit(objectComplementOf);
  }

  @NonNull @Override public DSVFCE convert(OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return visit(dataSomeValuesFrom);
  }

  @Override public @NonNull DECCE convert(OWLDataExactCardinality dataExactCardinality)
  {
    return visit(dataExactCardinality);
  }

  @Override public @NonNull OECCE convert(OWLObjectExactCardinality objectExactCardinality)
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

  @Override public @NonNull OOHSCE convert(OWLObjectHasSelf objectHasSelf)
  {
    return visit(objectHasSelf);
  }

  @NonNull private String generateCEID()
  {
    return "CEID" + this.classExpressionIndex++;
  }

}
