package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineSWRLBuiltInArgumentExtractor;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.converters.oo.DroolsOWLClassExpressionHandler;
import org.swrlapi.drools.converters.oo.DroolsOWLPropertyExpressionHandler;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.drools.swrl.VA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class creates SWRLAPI built-in arguments from their Drool's representation.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgumentExtractor extends DroolsExtractorBase
  implements TargetRuleEngineSWRLBuiltInArgumentExtractor
{
  @NonNull private final DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler;
  @NonNull private final DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler;

  public DroolsSWRLBuiltInArgumentExtractor(SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler,
    @NonNull DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler)
  {
    super(bridge);
    this.droolsOWLClassExpressionHandler = droolsOWLClassExpressionHandler;
    this.droolsOWLPropertyExpressionHandler = droolsOWLPropertyExpressionHandler;
  }

  @NonNull public SWRLVariable extract(@NonNull VA va) throws TargetSWRLRuleEngineException
  {
    IRI iri = prefixedName2IRI(va.getVariableName());
    return getOWLDataFactory().getSWRLVariable(iri);
  }

  @NonNull public SWRLClassBuiltInArgument extract(C c) throws TargetSWRLRuleEngineException
  {
    OWLClass cls = getDroolsOWLEntityExtractor().extract(c);

    return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
  }

  @NonNull public SWRLClassExpressionBuiltInArgument extract(CE ce) throws TargetSWRLRuleEngineException
  {
    OWLClassExpression classExpression = getDroolsOWLClassExpressionHandler().resolveOWLClassExpression(ce.getceid());

    return getSWRLBuiltInArgumentFactory().getClassExpressionBuiltInArgument(classExpression);
  }

  @NonNull public SWRLNamedIndividualBuiltInArgument extract(I i) throws TargetSWRLRuleEngineException
  {
    OWLNamedIndividual individual = getDroolsOWLEntityExtractor().extract(i);

    return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
  }

  @NonNull public SWRLObjectPropertyBuiltInArgument extract(OP op) throws TargetSWRLRuleEngineException
  {
    OWLObjectProperty property = getDroolsOWLEntityExtractor().extract(op);

    return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLObjectPropertyExpressionBuiltInArgument extract(OPE pe) throws TargetSWRLRuleEngineException
  {
    OWLObjectPropertyExpression propertyExpression = getDroolsOWLPropertyExpressionHandler()
      .resolveOWLObjectPropertyExpression(pe.getid());

    return getSWRLBuiltInArgumentFactory().getObjectPropertyExpressionBuiltInArgument(propertyExpression);
  }

  @NonNull public SWRLDataPropertyBuiltInArgument extract(DP dp) throws TargetSWRLRuleEngineException
  {
    OWLDataProperty property = getDroolsOWLEntityExtractor().extract(dp);

    return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLDataPropertyExpressionBuiltInArgument extract(DPE pe) throws TargetSWRLRuleEngineException
  {
    OWLDataPropertyExpression propertyExpression = getDroolsOWLPropertyExpressionHandler().resolveOWLDataPropertyExpression(pe.getid());

    return getSWRLBuiltInArgumentFactory().getDataPropertyExpressionBuiltInArgument(propertyExpression);
  }

  @NonNull public SWRLAnnotationPropertyBuiltInArgument extract(AP ap) throws TargetSWRLRuleEngineException
  {
    OWLAnnotationProperty property = getDroolsOWLEntityExtractor().extract(ap);

    return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLDatatypeBuiltInArgument extract(D d) throws TargetSWRLRuleEngineException
  {
    OWLDatatype datatype = getDroolsOWLEntityExtractor().extract(d);

    return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
  }

  @NonNull public SWRLLiteralBuiltInArgument extract(@NonNull L l) throws TargetSWRLRuleEngineException
  {
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(l);

    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
  }

  @NonNull public SWRLVariableBuiltInArgument extract(@NonNull UBA uba) throws TargetSWRLRuleEngineException
  {
    String variableName = uba.getVariableName();
    IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

    return getSWRLBuiltInArgumentFactory().getUnboundVariableBuiltInArgument(variableIRI);
  }

  @NonNull public SQWRLCollectionVariableBuiltInArgument extract(@NonNull SQWRLC sqwrlc)
    throws TargetSWRLRuleEngineException
  {
    String variableName = sqwrlc.getVariableName();
    IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

    return getSWRLBuiltInArgumentFactory()
      .getSQWRLCollectionVariableBuiltInArgument(variableIRI, sqwrlc.getQueryName(), sqwrlc.getCollectionName(),
        sqwrlc.getCollectionID());
  }

  @NonNull private DroolsOWLClassExpressionHandler getDroolsOWLClassExpressionHandler()
  {
    return this.droolsOWLClassExpressionHandler;
  }

  @NonNull private DroolsOWLPropertyExpressionHandler getDroolsOWLPropertyExpressionHandler()
  {
    return this.droolsOWLPropertyExpressionHandler;
  }
}
