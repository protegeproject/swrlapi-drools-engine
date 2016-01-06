package org.swrlapi.drools.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.converters.oo.DroolsSWRLBuiltInArgument2BAConverter;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.sqwrl.VPATH;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.BAP;
import org.swrlapi.drools.swrl.BAVNs;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInMethodRuntimeException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class is used to invoke SWRL built-ins from within a Drools rule.
 * <p>
 * Varargs seem to work inconsistently in this version of Drools. Hence the need for the repetitions for the invoke()
 * methods with varying numbers of arguments. We really want to replace this with a single call with a varargs argument.
 */
public class DroolsSWRLBuiltInInvoker
{
  @NonNull private final SWRLRuleEngineBridge bridge;
  @NonNull private final DroolsSWRLBuiltInArgument2BAConverter builtInArgumentConvertor;
  @NonNull private final DroolsSWRLBuiltInArgumentExtractor builtInArgumentExtractor;

  public static final int MAX_BUILTIN_ARGUMENTS = 11;

  @NonNull private final Map<@NonNull String, @NonNull List<@NonNull List<@NonNull SWRLBuiltInArgument>>> invocationPatternMap;

  public DroolsSWRLBuiltInInvoker(@NonNull SWRLRuleEngineBridge bridge)
  {
    this.bridge = bridge;
    this.builtInArgumentConvertor = new DroolsSWRLBuiltInArgument2BAConverter(bridge);
    this.builtInArgumentExtractor = new DroolsSWRLBuiltInArgumentExtractor(bridge);

    this.invocationPatternMap = new HashMap<>();
  }

  public void reset()
  {
    this.invocationPatternMap.clear();
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6, @NonNull BA ba7)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6, @NonNull BA ba7)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6, @NonNull BA ba7, @NonNull BA ba8)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6, BA ba7, @NonNull BA ba8)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6, @NonNull BA ba7, @NonNull BA ba8, @NonNull BA ba9)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6, BA ba7, @NonNull BA ba8, @NonNull BA ba9)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6, @NonNull BA ba7, @NonNull BA ba8, @NonNull BA ba9, @NonNull BA ba10)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    bas.add(ba10);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6, BA ba7, @NonNull BA ba8, @NonNull BA ba9,
    @NonNull BA ba10)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    bas.add(ba10);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5,
    @NonNull BA ba6, @NonNull BA ba7, @NonNull BA ba8, @NonNull BA ba9, @NonNull BA ba10, @NonNull BA ba11)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    bas.add(ba10);
    bas.add(ba11);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs bavns, @NonNull BA ba1, @NonNull BA ba2,
    @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6, BA ba7, @NonNull BA ba8, @NonNull BA ba9,
    @NonNull BA ba10, @NonNull BA ba11)
  {
    List<@NonNull BA> bas = new ArrayList<>();
    bas.add(ba1);
    bas.add(ba2);
    bas.add(ba3);
    bas.add(ba4);
    bas.add(ba5);
    bas.add(ba6);
    bas.add(ba7);
    bas.add(ba8);
    bas.add(ba9);
    bas.add(ba10);
    bas.add(ba11);
    return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
  }

  private String createInvocationPattern(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull List<@NonNull SWRLBuiltInArgument> vPathArguments,
    @NonNull List<@NonNull SWRLBuiltInArgument> builtInrguments)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(ruleName).append(".");
    sb.append(builtInName).append(".");
    sb.append("").append(builtInIndex).append(".");
    sb.append("").append(isInConsequent);

    for (SWRLBuiltInArgument vPathArgument : vPathArguments)
      sb.append(".").append(vPathArgument.toString());

    for (SWRLBuiltInArgument builtInArgument : builtInrguments)
      sb.append(".").append(builtInArgument.toString());

    return sb.toString();
  }

  @NonNull public List<@NonNull BAP> invoke(@NonNull String ruleName, @NonNull String builtInName, int builtInIndex,
    boolean isInConsequent, @NonNull VPATH vpath, @NonNull BAVNs argumentVariableNames, @NonNull List<@NonNull BA> bas)
  {
    List<@NonNull SWRLBuiltInArgument> builtInArguments = bas2SWRLBuiltInArguments(ruleName, builtInName,
      argumentVariableNames, bas);
    List<@NonNull SWRLBuiltInArgument> vPathArguments = vpath2SWRLBuiltInArguments(ruleName, builtInName, vpath);

    try {
      String invocationPattern = createInvocationPattern(ruleName, builtInName, builtInIndex, isInConsequent,
        vPathArguments, builtInArguments);

      if (!isInConsequent && hasInvocationPattern(invocationPattern)) {
        List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns = getInvocationPatternArguments(ruleName,
          builtInName, invocationPattern);
        return swrlBuiltInArgumentPatterns2BAPs(ruleName, builtInName, argumentPatterns);
      } else {
        List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns = getBridge()
          .invokeSWRLBuiltIn(ruleName, builtInName, builtInIndex, isInConsequent, builtInArguments);

        if (!isInConsequent)
          addInvocationPattern(builtInName, invocationPattern, argumentPatterns);

        return swrlBuiltInArgumentPatterns2BAPs(ruleName, builtInName, argumentPatterns);
      }
    } catch (Throwable e) {
      if (e instanceof SWRLBuiltInMethodRuntimeException) {
        Throwable cause = e.getCause();
        throw new SWRLAPIException(
          "runtime exception thrown by built-in " + builtInName + " in rule " + ruleName + ": " + (cause != null ?
            cause.toString() :
            ""), cause);
      } else if (e instanceof SWRLBuiltInException) {
        throw new SWRLAPIException(
          "built-in exception thrown by built-in " + builtInName + " in rule " + ruleName + ": " + (e != null ?
            e.getMessage() :
            ""), e);
      } else {
        throw new SWRLAPIException(
          "unknown exception " + e.getClass().getCanonicalName() + " thrown by built-in " + builtInName + " in rule "
            + ruleName + ": " + (e.getMessage() != null ? e.getMessage() : ""), e);
      }
    }
  }

  @NonNull private List<@NonNull SWRLBuiltInArgument> bas2SWRLBuiltInArguments(@NonNull String ruleName,
    @NonNull String builtInName, @NonNull BAVNs argumentVariableNames, @NonNull List<@NonNull BA> bas)
  {
    List<@NonNull SWRLBuiltInArgument> arguments = new ArrayList<>();

    if (argumentVariableNames.hasVariableNames() && argumentVariableNames.getNumberOfArguments() != bas.size())
      throw new TargetSWRLRuleEngineInternalException(
        "inconsistent variable names passed to built-in " + builtInName + " in rule " + ruleName);

    try {
      int argumentNumber = 0;
      for (BA ba : bas) {
        SWRLBuiltInArgument argument = ba.extract(getSWRLAtomArgumentExtractor());
        if (argumentVariableNames.hasVariableNames()
          && argumentVariableNames.getVariableNames().get(argumentNumber).length() != 0) {
          String variableName = argumentVariableNames.getVariableNames().get(argumentNumber);
          argument.setBoundVariableName(variableName); // This argument was bound from this original variable
        }
        arguments.add(argument);
        argumentNumber++;

      }
    } catch (TargetSWRLRuleEngineException e) {
      throw new TargetSWRLRuleEngineInternalException(
        "error extracting arguments from Drools when invoking built-in " + builtInName + " in rule " + ruleName + ": "
          + e.toString());
    }
    return arguments;
  }

  @NonNull private List<@NonNull SWRLBuiltInArgument> vpath2SWRLBuiltInArguments(@NonNull String ruleName,
    @NonNull String builtInName, @NonNull VPATH vpath)
  {
    List<@NonNull SWRLBuiltInArgument> arguments = new ArrayList<>();

    try {
      for (BA ba : vpath.getArguments()) {
        SWRLBuiltInArgument argument = ba.extract(getSWRLAtomArgumentExtractor());
        arguments.add(argument);
      }
    } catch (TargetSWRLRuleEngineException e) {
      throw new TargetSWRLRuleEngineInternalException(
        "error extracting path arguments from Drools when invoking built-in " + builtInName + " in rule " + ruleName
          + ": " + e.toString());
    }
    return arguments;
  }

  @NonNull private List<@NonNull BAP> swrlBuiltInArgumentPatterns2BAPs(@NonNull String ruleName,
    @NonNull String builtInName, @NonNull List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns)
  {
    List<@NonNull BAP> baps = new ArrayList<>();

    try {
      for (List<@NonNull SWRLBuiltInArgument> argumentPattern : argumentPatterns) {
        BAP bap = new BAP();
        for (SWRLBuiltInArgument argument : argumentPattern) {
          if (argument.isVariable() && argument.asVariable().hasBuiltInResult()) {
            Optional<@NonNull SWRLBuiltInArgument> builtInResult = argument.asVariable().getBuiltInResult();
            if (builtInResult.isPresent())
              argument = builtInResult.get();
          }
          BA ba = getSWRLBuiltInArgumentConverter().convert(argument);
          bap.addArgument(ba);
        }
        baps.add(bap);
      }
    } catch (RuntimeException e) {
      throw new TargetSWRLRuleEngineInternalException(
        "error converting return arguments after invoking built-in " + builtInName + " in rule " + ruleName + ": " + e
          .toString());
    }
    return baps;
  }

  private boolean hasInvocationPattern(@NonNull String invocationPattern)
  {
    return this.invocationPatternMap.containsKey(invocationPattern);
  }

  private void addInvocationPattern(@NonNull String builtInName, @NonNull String invocationPattern,
    List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns)
  {
    if (!this.invocationPatternMap.containsKey(invocationPattern))
      this.invocationPatternMap.put(invocationPattern, argumentPatterns);
    else
      throw new TargetSWRLRuleEngineInternalException("inconsistent invocation pattern in " + builtInName + " in rule");
  }

  private List<@NonNull List<@NonNull SWRLBuiltInArgument>> getInvocationPatternArguments(@NonNull String ruleName,
    @NonNull String builtInName, @NonNull String invocationPattern)
  {
    if (this.invocationPatternMap.containsKey(invocationPattern))
      return this.invocationPatternMap.get(invocationPattern);
    else
      throw new TargetSWRLRuleEngineInternalException(
        "unknown invocation pattern in " + builtInName + " in rule " + ruleName);
  }

  @NonNull private DroolsSWRLBuiltInArgument2BAConverter getSWRLBuiltInArgumentConverter()
  {
    return this.builtInArgumentConvertor;
  }

  @NonNull private DroolsSWRLBuiltInArgumentExtractor getSWRLAtomArgumentExtractor()
  {
    return this.builtInArgumentExtractor;
  }

  @NonNull private SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
