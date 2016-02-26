package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSQWRLQueryConverter;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.sqwrl.SQWRLNames;
import org.swrlapi.sqwrl.SQWRLQuery;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts a SWRLAPI SQWRL query to its Drools representation.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class DroolsSQWRLQuery2DRLConverter extends DroolsDRLConverterBase implements TargetRuleEngineSQWRLQueryConverter
{
  @NonNull private final DroolsSWRLBodyAtom2DRLConverter bodyAtom2DRLConverter;
  @NonNull private final DroolsSWRLHeadAtom2DRLConverter headAtom2DRLConverter;
  @NonNull private final DroolsSWRLRuleEngine droolsEngine;

  public DroolsSQWRLQuery2DRLConverter(@NonNull SWRLRuleEngineBridge bridge, @NonNull DroolsSWRLRuleEngine droolsEngine,
    @NonNull DroolsOWLClassExpression2DRLConverter classExpressionConverter,
    @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter)
  {
    super(bridge);

    this.droolsEngine = droolsEngine;
    this.bodyAtom2DRLConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge, classExpressionConverter,
      propertyExpressionConverter);
    this.headAtom2DRLConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge, classExpressionConverter,
      propertyExpressionConverter);
  }

  public void reset()
  {
    this.bodyAtom2DRLConverter.reset();
    this.headAtom2DRLConverter.reset();
  }

  @Override public void convert(@NonNull SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    getDroolsSWRLBodyAtom2DRLConverter().reset();
    getDroolsSWRLHeadAtom2DRLConverter().reset();

    sqwrlQuery2DRL(query);
  }

  private void sqwrlQuery2DRL(@NonNull SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    if (!query.hasSQWRLCollections())
      sqwrlNonCollectionQuery2DRL(query);
    else
      sqwrlCollectionQuery2DRL(query);
  }

  private void sqwrlNonCollectionQuery2DRL(@NonNull SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    Set<@NonNull String> previouslyEncounteredVariableNames = new HashSet<>();
    String ruleName = query.getQueryName();
    String drlRule = getQueryPreamble(ruleName);

    for (SWRLAtom atom : query.getBodyAtoms())
      drlRule +=
        "\n   " + getDroolsSWRLBodyAtom2DRLConverter().convert(atom, previouslyEncounteredVariableNames) + " ";

    drlRule = addQueryThenClause(drlRule);

    for (SWRLAtom atom : query.getHeadAtoms())
      drlRule += "\n   " + getDroolsSWRLHeadAtom2DRLConverter().convert(atom) + " ";

    drlRule = addQueryEndClause(drlRule);

    // System.err.println("----------------------------------");
    // System.err.println("SQWRL: " + query.getQueryName());
    // System.err.println("DRL:\n" + drlRule);

    getDroolsSWRLEngine().defineDRLSQWRLPhase1Rule(query.getQueryName(), ruleName, drlRule);
  }

  private void sqwrlCollectionQuery2DRL(@NonNull SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    Set<@NonNull String> previouslyEncounteredVariableNames = new HashSet<>();
    String queryName = query.getQueryName();
    String phase1RuleName = queryName + "-makeCollection";
    String phase2RuleName = queryName + "-operateCollection";
    String drlPhase1Rule = getQueryPreamble(phase1RuleName);
    String drlPhase2Rule = getQueryPreamble(phase2RuleName);

    for (SWRLAtom atom : query.getSQWRLPhase1BodyAtoms())
      drlPhase1Rule +=
        "\n  " + getDroolsSWRLBodyAtom2DRLConverter().convert(atom, previouslyEncounteredVariableNames) + " ";

    drlPhase1Rule = addQueryThenClause(drlPhase1Rule);

    if (query.hasSQWRLCollections()) { // Assert existence of all relevant collections returned from collection
      // construction
      // built-ins
      try {
        for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
          String collectionVariableName = atom.getArgumentVariableName(0);
          drlPhase1Rule += "\n  sqwrlInferrer.infer(" + getDroolsSWRLVariable2NameConverter()
            .variableName2DRL(collectionVariableName) + "); ";
        }
      } catch (RuntimeException e) {
        throw new TargetSWRLRuleEngineException(
          "error processing SQWRL collection make in query " + queryName + ": " + (e.getMessage() != null ?
            e.getMessage() :
            ""), e);
      }
    }
    drlPhase1Rule = addQueryEndClause(drlPhase1Rule);
    getDroolsSWRLEngine().defineDRLSQWRLPhase1Rule(queryName, phase1RuleName, drlPhase1Rule);

    previouslyEncounteredVariableNames.clear();

    if (query.hasSQWRLCollections()) { // Match relevant collections
      try {
        for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
          String collectionVariableName = atom.getArgumentVariableName(0);
          if (!previouslyEncounteredVariableNames.contains(collectionVariableName)) {
            String collection = getDroolsSWRLVariable2NameConverter()
              .variableName2DRL(collectionVariableName);
            drlPhase2Rule += "\n " + collection + ":" + DroolsNames.SQWRL_COLLECTION_CLASS_NAME + "("
              + DroolsNames.QUERY_NAME_FIELD_NAME + "==\"" + queryName + "\", " + DroolsNames.COLLECTION_NAME_FIELD_NAME
              + "==\"" + collectionVariableName + "\")";
            previouslyEncounteredVariableNames.add(collectionVariableName);
          }
        }
      } catch (RuntimeException e) {
        throw new TargetSWRLRuleEngineException(
          "error processing SQWRL collection operate in query " + queryName + ": " + (e.getMessage() != null ?
            e.getMessage() :
            ""), e);
      }
    }
    for (SWRLAtom atom : query.getSQWRLPhase2BodyAtoms())
      drlPhase2Rule +=
        "\n  " + getDroolsSWRLBodyAtom2DRLConverter().convert(atom, previouslyEncounteredVariableNames) + " ";

    drlPhase2Rule = addQueryThenClause(drlPhase2Rule);
    for (SWRLAtom atom : query.getHeadAtoms())
      drlPhase2Rule += "\n  " + getDroolsSWRLHeadAtom2DRLConverter().convert(atom);

    drlPhase2Rule = addQueryEndClause(drlPhase2Rule);
    getDroolsSWRLEngine().defineDRLSQWRLPhase2Rule(queryName, phase2RuleName, drlPhase2Rule);
  }

  @NonNull private String getQueryPreamble(@NonNull String queryName)
  {
    return "rule \"" + queryName + "\" \nwhen ";
  }

  @NonNull private String addQueryEndClause(@NonNull String queryText)
  {
    return queryText + "\nend";
  }

  @NonNull private String addQueryThenClause(@NonNull String queryText)
  {
    return queryText + "\nthen ";
  }

  @NonNull private DroolsSWRLBodyAtom2DRLConverter getDroolsSWRLBodyAtom2DRLConverter()
  {
    return this.bodyAtom2DRLConverter;
  }

  @NonNull private DroolsSWRLHeadAtom2DRLConverter getDroolsSWRLHeadAtom2DRLConverter()
  {
    return this.headAtom2DRLConverter;
  }

  @NonNull private DroolsSWRLRuleEngine getDroolsSWRLEngine()
  {
    return this.droolsEngine;
  }
}
