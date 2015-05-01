package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.sqwrl.SQWRLNames;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.TargetRuleEngineSQWRLQueryConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts a SWRLAPI SQWRL query to its Drools representation.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class DroolsSQWRLQuery2DRLConverter extends DroolsConverterBase implements TargetRuleEngineSQWRLQueryConverter
{
  private final DroolsSWRLBodyAtom2DRLConverter bodyAtom2DRLConverter;
  private final DroolsSWRLHeadAtom2DRLConverter headAtom2DRLConverter;

  private final DroolsSWRLRuleEngine droolsEngine;

  public DroolsSQWRLQuery2DRLConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine,
      DroolsOWLClassExpressionConverter classExpressionConverter,
      DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
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

  @Override
  public void convert(SQWRLQuery query) throws TargetSWRLRuleEngineException, SWRLBuiltInException
  {
    getDroolsSWRLBodyAtomConverter().reset();
    getDroolsSWRLHeadAtomConverter().reset();

    sqwrlQuery2DRL(query);
  }

  private void sqwrlQuery2DRL(SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    if (!query.hasSQWRLCollections())
      sqwrlNonCollectionQuery2DRL(query);
    else
      sqwrlCollectionQuery2DRL(query);
  }

  private void sqwrlNonCollectionQuery2DRL(SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    Set<String> previouslyEncounteredVariablePrefixedNames = new HashSet<>();
    String ruleName = query.getQueryName();
    String drlRule = getQueryPreamble(ruleName);

    for (SWRLAtom atom : query.getBodyAtoms())
      drlRule += "\n   " + getDroolsSWRLBodyAtomConverter().convert(atom, previouslyEncounteredVariablePrefixedNames)
      + " ";

    drlRule = addQueryThenClause(drlRule);

    for (SWRLAtom atom : query.getHeadAtoms())
      drlRule += "\n   " + getDroolsSWRLHeadAtomConverter().convert(atom) + " ";

    drlRule = addQueryEndClause(drlRule);

    // System.err.println("----------------------------------");
    // System.err.println("SQWRL: " + query.getQueryName());
    // System.err.println("DRL:\n" + drlRule);

    getDroolsEngine().defineDRLSQWRLPhase1Rule(query.getQueryName(), ruleName, drlRule);
  }

  private void sqwrlCollectionQuery2DRL(SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    Set<String> previouslyEncounteredVariablePrefixedNames = new HashSet<>();
    String queryName = query.getQueryName();
    String phase1RuleName = queryName + "-makeCollection";
    String phase2RuleName = queryName + "-operateCollection";
    String drlPhase1Rule = getQueryPreamble(phase1RuleName);
    String drlPhase2Rule = getQueryPreamble(phase2RuleName);

    for (SWRLAtom atom : query.getSQWRLPhase1BodyAtoms())
      drlPhase1Rule += "\n  "
          + getDroolsSWRLBodyAtomConverter().convert(atom, previouslyEncounteredVariablePrefixedNames) + " ";

    drlPhase1Rule = addQueryThenClause(drlPhase1Rule);

    if (query.hasSQWRLCollections()) { // Assert existence of all relevant collections returned from collection
      // construction
      // built-ins
      try {
        for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
          String collectionVariablePrefixedName = atom.getArgumentVariablePrefixedName(0);
          drlPhase1Rule += "\n  sqwrlInferrer.infer("
              + getDroolsSWRLVariableConverter().variablePrefixedName2DRL(collectionVariablePrefixedName) + "); ";
        }
      } catch (RuntimeException e) {
        throw new TargetSWRLRuleEngineException("error processing SQWRL collection make in query " + queryName + ": "
            + e.getMessage(), e);
      }
    }
    drlPhase1Rule = addQueryEndClause(drlPhase1Rule);
    getDroolsEngine().defineDRLSQWRLPhase1Rule(queryName, phase1RuleName, drlPhase1Rule);

    previouslyEncounteredVariablePrefixedNames.clear();

    if (query.hasSQWRLCollections()) { // Match relevant collections
      try {
        for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
          String collectionVariablePrefixedName = atom.getArgumentVariablePrefixedName(0);
          if (!previouslyEncounteredVariablePrefixedNames.contains(collectionVariablePrefixedName)) {
            String collection = getDroolsSWRLVariableConverter().variablePrefixedName2DRL(
                collectionVariablePrefixedName);
            drlPhase2Rule += "\n " + collection + ":" + DroolsNames.SQWRL_COLLECTION_CLASS_NAME + "("
                + DroolsNames.QUERY_NAME_FIELD_NAME + "==\"" + queryName + "\", "
                + DroolsNames.COLLECTION_NAME_FIELD_NAME + "==\"" + collectionVariablePrefixedName + "\")";
            previouslyEncounteredVariablePrefixedNames.add(collectionVariablePrefixedName);
          }
        }
      } catch (RuntimeException e) {
        throw new TargetSWRLRuleEngineException("error processing SQWRL collection operate in query " + queryName
            + ": " + e.getMessage(), e);
      }
    }
    for (SWRLAtom atom : query.getSQWRLPhase2BodyAtoms())
      drlPhase2Rule += "\n  "
          + getDroolsSWRLBodyAtomConverter().convert(atom, previouslyEncounteredVariablePrefixedNames) + " ";

    drlPhase2Rule = addQueryThenClause(drlPhase2Rule);
    for (SWRLAtom atom : query.getHeadAtoms())
      drlPhase2Rule += "\n  " + getDroolsSWRLHeadAtomConverter().convert(atom);

    drlPhase2Rule = addQueryEndClause(drlPhase2Rule);
    getDroolsEngine().defineDRLSQWRLPhase2Rule(queryName, phase2RuleName, drlPhase2Rule);
  }

  private String getQueryPreamble(String queryName)
  {
    return "rule \"" + queryName + "\" \nwhen ";
  }

  private String addQueryEndClause(String queryText)
  {
    return queryText + "\nend";
  }

  private String addQueryThenClause(String queryText)
  {
    return queryText + "\nthen ";
  }

  private DroolsSWRLBodyAtom2DRLConverter getDroolsSWRLBodyAtomConverter()
  {
    return this.bodyAtom2DRLConverter;
  }

  private DroolsSWRLHeadAtom2DRLConverter getDroolsSWRLHeadAtomConverter()
  {
    return this.headAtom2DRLConverter;
  }

  private DroolsSWRLRuleEngine getDroolsEngine()
  {
    return this.droolsEngine;
  }
}
