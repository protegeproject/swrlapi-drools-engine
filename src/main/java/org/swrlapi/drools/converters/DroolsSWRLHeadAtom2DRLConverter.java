package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLHeadAtomConverter;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLBuiltInInvoker;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL head atoms to a their DRL representation for use in rules.
 * <p>
 * Head and body atoms are converted differently - hence the need for two converters. Body atom converters must also
 * know the variables defined by previous atoms because a different syntax is required in DRL for declaring a variable
 * vs. referring to one that is already declared. In the head, all variables are guaranteed to have already been
 * declared because SWRL demands this.
 *
 * @see org.swrlapi.drools.converters.DroolsSWRLBodyAtom2DRLConverter
 */
public class DroolsSWRLHeadAtom2DRLConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLHeadAtomConverter<String>
{
	private final DroolsSWRLHeadAtomArgument2DRLConverter headAtomArgumentConverter;
	private final DroolsSWRLBuiltInArgument2DRLConverter builtInArgumentConverter;
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final DroolsOWLClassExpressionConverter classExpressionConverter;

	private int inferredAxiomVariableIndex, builtInIndexInHead;

	public DroolsSWRLHeadAtom2DRLConverter(SWRLRuleEngineBridge bridge,
			DroolsOWLClassExpressionConverter classExpressionConverter,
			DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
	{
		super(bridge);

		this.headAtomArgumentConverter = new DroolsSWRLHeadAtomArgument2DRLConverter(bridge);
		this.builtInArgumentConverter = new DroolsSWRLBuiltInArgument2DRLConverter(bridge);
		this.classExpressionConverter = classExpressionConverter;
		this.propertyExpressionConverter = propertyExpressionConverter;

		this.inferredAxiomVariableIndex = 0;
		this.builtInIndexInHead = 0;
	}

	public void reset()
	{
		this.inferredAxiomVariableIndex = 0;
		this.builtInIndexInHead = 0;
	}

	public String convert(SWRLAtom atom) throws TargetRuleEngineException
	{ // TODO Visitor to replace instanceof
		if (atom instanceof SWRLDataRangeAtom) {
			return convert((SWRLDataRangeAtom)atom);
		} else if (atom instanceof SWRLClassAtom) {
			return convert((SWRLClassAtom)atom);
		} else if (atom instanceof SWRLDataPropertyAtom) {
			return convert((SWRLDataPropertyAtom)atom);
		} else if (atom instanceof SWRLObjectPropertyAtom) {
			return convert((SWRLObjectPropertyAtom)atom);
		} else if (atom instanceof SWRLSameIndividualAtom) {
			return convert((SWRLSameIndividualAtom)atom);
		} else if (atom instanceof SWRLDifferentIndividualsAtom) {
			return convert((SWRLDifferentIndividualsAtom)atom);
		} else if (atom instanceof SWRLAPIBuiltInAtom) {
			return convert((SWRLAPIBuiltInAtom)atom);
		} else
			throw new RuntimeException("unknown SWRL atom type " + atom.getClass().getCanonicalName());
	}

	@Override
	public String convert(SWRLClassAtom atom) throws TargetRuleEngineException
	{
		String className = getOWLClassExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument = atom.getArgument();
		String caaVariable = "caa" + this.inferredAxiomVariableIndex++;
		String representation = DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + " " + caaVariable + "=new "
				+ DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + "(" + addQuotes(className) + ", ";

		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument) + ")";
		representation += "); ";
		representation += "inferrer.infer(" + caaVariable + "); ";

		return representation;
	}

	@Override
	public String convert(SWRLObjectPropertyAtom atom) throws TargetRuleEngineException
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String opaaVariable = "opaa" + this.inferredAxiomVariableIndex++;
		String representation = DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + " " + opaaVariable + "=new "
				+ DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
		representation += ", " + addQuotes(propertyID) + ", ";
		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
		representation += "); ";
		representation += "inferrer.infer(" + opaaVariable + "); ";

		return representation;
	}

	@Override
	public String convert(SWRLDataPropertyAtom atom) throws TargetRuleEngineException
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLDArgument argument2 = atom.getSecondArgument();
		String dpaaVariable = "dpaa" + this.inferredAxiomVariableIndex++;
		String representation = DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + " " + dpaaVariable + "=new "
				+ DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
		representation += ", " + addQuotes(propertyID) + ", ";
		representation += "new " + DroolsNames.LITERAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
		representation += "); ";
		representation += "inferrer.infer(" + dpaaVariable + "); ";

		return representation;
	}

	@Override
	public String convert(SWRLSameIndividualAtom atom) throws TargetRuleEngineException
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String siaVariable = "sia" + this.inferredAxiomVariableIndex++;
		String representation = DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + " " + siaVariable + "=new "
				+ DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + "(";

		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
		representation += ", ";
		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
		representation += "); ";
		representation += "inferrer.infer(" + siaVariable + "); ";

		return representation;
	}

	@Override
	public String convert(SWRLDifferentIndividualsAtom atom) throws TargetRuleEngineException
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String diaVariable = "dia" + this.inferredAxiomVariableIndex++;
		String representation = DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + " " + diaVariable + "=new "
				+ DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + "(";

		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
		representation += ", ";
		representation += "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "("
				+ getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
		representation += "); ";
		representation += "inferrer.infer(" + diaVariable + "); ";

		return representation;
	}

	@Override
	public String convert(SWRLAPIBuiltInAtom builtInAtom) throws TargetRuleEngineException
	{
		String builtInName = builtInAtom.getBuiltInPrefixedName();
		String ruleName = builtInAtom.getRuleName();
		String representation = "invoker.invoke(\"" + ruleName + "\", \"" + builtInName + "\", " + this.builtInIndexInHead
				+ ", true, ";
		boolean isFirst = true;

		if (builtInAtom.getNumberOfArguments() > DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS)
			throw new TargetRuleEngineException("A maximum of " + DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS
					+ " can be passed to built-ins");

		for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
			if (!isFirst)
				representation += ", ";
			representation += getSWRLBuiltInArgumentConverter().convert(argument);
			isFirst = false;
		}
		representation += "); ";

		this.builtInIndexInHead++;

		return representation;
	}

	@Override
	public String convert(SWRLDataRangeAtom atom) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
	}

	private DroolsSWRLHeadAtomArgument2DRLConverter getSWRLHeadAtomArgumentConverter()
	{
		return this.headAtomArgumentConverter;
	}

	private DroolsSWRLBuiltInArgument2DRLConverter getSWRLBuiltInArgumentConverter()
	{
		return this.builtInArgumentConverter;
	}

	private DroolsOWLPropertyExpressionConverter getOWLPropertyExpressionConverter()
	{
		return this.propertyExpressionConverter;
	}

	private DroolsOWLClassExpressionConverter getOWLClassExpressionConverter()
	{
		return this.classExpressionConverter;
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
