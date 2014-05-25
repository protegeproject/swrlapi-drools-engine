package org.swrlapi.drools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.converters.DroolsSWRLBuiltInArgument2BAConverter;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.drools.sqwrl.VPATH;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.BAP;
import org.swrlapi.drools.swrl.BAVNs;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.BuiltInMethodRuntimeException;
import org.swrlapi.exceptions.TargetRuleEngineException;

// Varargs seem to work inconsistently in Drools. Hence the need for the  repetitions for the BA arguments. 
// We really want to replace this with  with a single call with a varargs argument. 

/**
 * This class is used to invoke SWRL built-ins from within a Drools rule.
 * <p>
 * Varargs seem to work inconsistently in this version of Drools. Hence the need for the repetitions for the invoke()
 * methods with varying numbers of arguments. We really want to replace this with a single call with a varargs argument.
 */
public class DroolsSWRLBuiltInInvoker
{
	private final SWRLRuleEngineBridge bridge;
	private final DroolsSWRLBuiltInArgument2BAConverter builtInArgumentConvertor;
	private final DroolsAA2SWRLAtomArgumentExtractor atomArgumentExtractor;

	public static final int MaxBuiltInArguments = 11;

	private final Map<String, List<List<SWRLBuiltInArgument>>> invocationPatternMap;

	public DroolsSWRLBuiltInInvoker(SWRLRuleEngineBridge bridge)
	{
		this.bridge = bridge;
		this.builtInArgumentConvertor = new DroolsSWRLBuiltInArgument2BAConverter(bridge);
		this.atomArgumentExtractor = new DroolsAA2SWRLAtomArgumentExtractor(bridge);

		this.invocationPatternMap = new HashMap<String, List<List<SWRLBuiltInArgument>>>();
	}

	public void reset()
	{
		invocationPatternMap.clear();
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1, BA ba2)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		bas.add(ba6);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		bas.add(ba6);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		bas.add(ba6);
		bas.add(ba7);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, new VPATH(), new BAVNs(), bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7)
	{
		List<BA> bas = new ArrayList<BA>();
		bas.add(ba1);
		bas.add(ba2);
		bas.add(ba3);
		bas.add(ba4);
		bas.add(ba5);
		bas.add(ba6);
		bas.add(ba7);
		return invoke(ruleName, builtInName, builtInIndex, isInConsequent, vpath, bavns, bas);
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9, BA ba10)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9, BA ba10)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, BA ba1,
			BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9, BA ba10, BA ba11)
	{
		List<BA> bas = new ArrayList<BA>();
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

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs bavns, BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9, BA ba10, BA ba11)
	{
		List<BA> bas = new ArrayList<BA>();
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

	private String createInvocationPattern(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent,
			List<SWRLBuiltInArgument> vPathArguments, List<SWRLBuiltInArgument> builtInrguments)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(ruleName + ".");
		sb.append(builtInName + ".");
		sb.append("" + builtInIndex + ".");
		sb.append("" + isInConsequent);

		for (SWRLBuiltInArgument vPathArgument : vPathArguments)
			sb.append("." + vPathArgument.toString());

		for (SWRLBuiltInArgument builtInArgument : builtInrguments)
			sb.append("." + builtInArgument.toString());

		return sb.toString();
	}

	public List<BAP> invoke(String ruleName, String builtInName, int builtInIndex, boolean isInConsequent, VPATH vpath,
			BAVNs argumentVariableNames, List<BA> bas)
	{
		List<SWRLBuiltInArgument> builtInArguments = bas2SWRLBuiltInArguments(ruleName, builtInName, argumentVariableNames,
				bas);
		List<SWRLBuiltInArgument> vPathArguments = vpath2SWRLBuiltInArguments(ruleName, builtInName, vpath);

		try {
			String invocationPattern = createInvocationPattern(ruleName, builtInName, builtInIndex, isInConsequent,
					vPathArguments, builtInArguments);

			if (!isInConsequent && hasInvocationPattern(invocationPattern)) {
				List<List<SWRLBuiltInArgument>> argumentPatterns = getInvocationPatternArguments(ruleName, builtInName,
						invocationPattern);
				return swrlBuiltInArgumentPatterns2BAPs(ruleName, builtInName, argumentPatterns);
			} else {
				List<List<SWRLBuiltInArgument>> argumentPatterns = getBridge().invokeSWRLBuiltIn(ruleName, builtInName,
						builtInIndex, isInConsequent, builtInArguments);

				if (!isInConsequent)
					addInvocationPattern(ruleName, builtInName, invocationPattern, argumentPatterns);

				return swrlBuiltInArgumentPatterns2BAPs(ruleName, builtInName, argumentPatterns);
			}
		} catch (Throwable e) {
			if (e instanceof BuiltInMethodRuntimeException) {
				Throwable cause = e.getCause();
				cause.printStackTrace();
				throw new RuntimeException("runtime exception thrown by built-in " + builtInName + " in rule " + ruleName
						+ ": " + cause.toString(), cause);
			} else if (e instanceof BuiltInException) {
				throw new RuntimeException("built-in exception thrown by built-in " + builtInName + " in rule " + ruleName
						+ ": " + e.getMessage(), e);
			} else {
				e.printStackTrace();
				throw new RuntimeException("unknown exception " + e.getClass().getCanonicalName() + " thrown by built-in "
						+ builtInName + " in rule " + ruleName + ": " + e.getMessage(), e);
			}
		}
	}

	private List<SWRLBuiltInArgument> bas2SWRLBuiltInArguments(String ruleName, String builtInName,
			BAVNs argumentVariableNames, List<BA> bas)
	{
		List<SWRLBuiltInArgument> arguments = new ArrayList<SWRLBuiltInArgument>();

		if (argumentVariableNames.hasVariableNames() && argumentVariableNames.getNumberOfArguments() != bas.size())
			throw new RuntimeException("internal error: inconsistent variable names passed to built-in " + builtInName
					+ " in rule " + ruleName);

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
		} catch (TargetRuleEngineException e) {
			throw new RuntimeException("error extracting arguments from Drools when invoking built-in " + builtInName
					+ " in rule " + ruleName + ": " + e.toString());
		}
		return arguments;
	}

	private List<SWRLBuiltInArgument> vpath2SWRLBuiltInArguments(String ruleName, String builtInName, VPATH vpath)
	{
		List<SWRLBuiltInArgument> arguments = new ArrayList<SWRLBuiltInArgument>();

		try {
			for (BA ba : vpath.getArguments()) {
				SWRLBuiltInArgument argument = ba.extract(getSWRLAtomArgumentExtractor());
				arguments.add(argument);
			}
		} catch (TargetRuleEngineException e) {
			throw new RuntimeException("error extracting path arguments from Drools when invoking built-in " + builtInName
					+ " in rule " + ruleName + ": " + e.toString());
		}
		return arguments;
	}

	private List<BAP> swrlBuiltInArgumentPatterns2BAPs(String ruleName, String builtInName,
			List<List<SWRLBuiltInArgument>> argumentPatterns)
	{
		List<BAP> baps = new ArrayList<BAP>();

		try {
			for (List<SWRLBuiltInArgument> argumentPattern : argumentPatterns) {
				BAP bap = new BAP();
				for (SWRLBuiltInArgument argument : argumentPattern) {
					if (argument.isVariable() && argument.asVariable().hasBuiltInResult())
						argument = argument.asVariable().getBuiltInResult();

					BA ba = getSWRLBuiltInArgumentConverter().convert(argument);
					bap.addArgument(ba);
				}
				baps.add(bap);
			}
		} catch (TargetRuleEngineException e) {
			throw new RuntimeException("error converting return arguments after invoking built-in " + builtInName
					+ " in rule " + ruleName + ": " + e.toString());
		}
		return baps;
	}

	private boolean hasInvocationPattern(String invocationPattern)
	{
		return this.invocationPatternMap.containsKey(invocationPattern);
	}

	private void addInvocationPattern(String ruleName, String builtInName, String invocationPattern,
			List<List<SWRLBuiltInArgument>> argumentPatterns)
	{
		if (!this.invocationPatternMap.containsKey(invocationPattern))
			this.invocationPatternMap.put(invocationPattern, argumentPatterns);
		else
			throw new RuntimeException("internal error: inconsistent invocation pattern in " + builtInName + " in rule");
	}

	private List<List<SWRLBuiltInArgument>> getInvocationPatternArguments(String ruleName, String builtInName,
			String invocationPattern)
	{
		if (this.invocationPatternMap.containsKey(invocationPattern))
			return this.invocationPatternMap.get(invocationPattern);
		else
			throw new RuntimeException("internal error: unknown invocation pattern in " + builtInName + " in rule "
					+ ruleName);
	}

	private DroolsSWRLBuiltInArgument2BAConverter getSWRLBuiltInArgumentConverter()
	{
		return this.builtInArgumentConvertor;
	}

	private DroolsAA2SWRLAtomArgumentExtractor getSWRLAtomArgumentExtractor()
	{
		return this.atomArgumentExtractor;
	}

	private SWRLRuleEngineBridge getBridge()
	{
		return this.bridge;
	}
}
