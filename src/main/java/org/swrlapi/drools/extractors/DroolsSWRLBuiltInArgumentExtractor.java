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
  public DroolsSWRLBuiltInArgumentExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public SWRLVariable extract(@NonNull VA va) throws TargetSWRLRuleEngineException
  {
    IRI iri = prefixedName2IRI(va.getVariableName());
    return getOWLDataFactory().getSWRLVariable(iri);
  }

  public @NonNull SWRLClassBuiltInArgument extract(C c) throws TargetSWRLRuleEngineException
  {
    OWLClass cls = getDroolsOWLEntityExtractor().extract(c);

    return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
  }

  public @NonNull SWRLClassExpressionBuiltInArgument extract(CE ce) throws TargetSWRLRuleEngineException
  {
    OWLClassExpression classExpression = getOWLObjectResolver().resolveOWLClassExpression(ce.getceid());

    return getSWRLBuiltInArgumentFactory().getClassExpressionBuiltInArgument(classExpression);
  }

  public @NonNull SWRLNamedIndividualBuiltInArgument extract(I i) throws TargetSWRLRuleEngineException
  {
    OWLNamedIndividual individual = getDroolsOWLEntityExtractor().extract(i);

    return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
  }

  public @NonNull SWRLObjectPropertyBuiltInArgument extract(OP op) throws TargetSWRLRuleEngineException
  {
    OWLObjectProperty property = getDroolsOWLEntityExtractor().extract(op);

    return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
  }

  public @NonNull SWRLObjectPropertyExpressionBuiltInArgument extract(OPE pe) throws TargetSWRLRuleEngineException
  {
    OWLObjectPropertyExpression propertyExpression = getOWLObjectResolver()
      .resolveOWLObjectPropertyExpression(pe.getid());

    return getSWRLBuiltInArgumentFactory().getObjectPropertyExpressionBuiltInArgument(propertyExpression);
  }

  public @NonNull SWRLDataPropertyBuiltInArgument extract(DP dp) throws TargetSWRLRuleEngineException
  {
    OWLDataProperty property = getDroolsOWLEntityExtractor().extract(dp);

    return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
  }

  public @NonNull SWRLDataPropertyExpressionBuiltInArgument extract(DPE pe) throws TargetSWRLRuleEngineException
  {
    OWLDataPropertyExpression propertyExpression = getOWLObjectResolver().resolveOWLDataPropertyExpression(pe.getid());

    return getSWRLBuiltInArgumentFactory().getDataPropertyExpressionBuiltInArgument(propertyExpression);
  }

  public @NonNull SWRLAnnotationPropertyBuiltInArgument extract(AP ap) throws TargetSWRLRuleEngineException
  {
    OWLAnnotationProperty property = getDroolsOWLEntityExtractor().extract(ap);

    return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
  }

  public @NonNull SWRLDatatypeBuiltInArgument extract(D d) throws TargetSWRLRuleEngineException
  {
    OWLDatatype datatype = getDroolsOWLEntityExtractor().extract(d);

    return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
  }

  public @NonNull SWRLLiteralBuiltInArgument extract(@NonNull L l) throws TargetSWRLRuleEngineException
  {
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(l);

    return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
  }

  public @NonNull SWRLVariableBuiltInArgument extract(@NonNull UBA uba) throws TargetSWRLRuleEngineException
  {
    String variableName = uba.getVariableName();
    IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

    return getSWRLBuiltInArgumentFactory().getUnboundVariableBuiltInArgument(variableIRI);
  }

  public @NonNull SQWRLCollectionVariableBuiltInArgument extract(@NonNull SQWRLC sqwrlc)
    throws TargetSWRLRuleEngineException
  {
    String variableName = sqwrlc.getVariableName();
    IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

    return getSWRLBuiltInArgumentFactory()
      .getSQWRLCollectionVariableBuiltInArgument(variableIRI, sqwrlc.getQueryName(), sqwrlc.getCollectionName(),
        sqwrlc.getCollectionID());
  }
}
