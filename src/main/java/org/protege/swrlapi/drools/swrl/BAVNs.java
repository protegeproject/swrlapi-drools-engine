package org.protege.swrlapi.drools.swrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the names of variables passed to built-ins. Non variable arguments positions are represented by
 * empty strings.
 */
public class BAVNs
{
	public static final int MaxArguments = 10;

	private final List<String> variableNames = new ArrayList<String>();

	/*
	 * TODO this does not seem to work in Drools public BAVNs(String... variableNames) { for (String variableName :
	 * variableNames) { this.variableNames.add(variableName); } }
	 */

	public BAVNs()
	{
	}

	public BAVNs(String v1)
	{
		this.variableNames.add(v1);
	}

	public BAVNs(String v1, String v2)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
	}

	public BAVNs(String v1, String v2, String v3)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
	}

	public BAVNs(String v1, String v2, String v3, String v4)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5, String v6)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
		this.variableNames.add(v6);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5, String v6, String v7)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
		this.variableNames.add(v6);
		this.variableNames.add(v7);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5, String v6, String v7, String v8)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
		this.variableNames.add(v6);
		this.variableNames.add(v7);
		this.variableNames.add(v8);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5, String v6, String v7, String v8, String v9)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
		this.variableNames.add(v6);
		this.variableNames.add(v7);
		this.variableNames.add(v8);
		this.variableNames.add(v9);
	}

	public BAVNs(String v1, String v2, String v3, String v4, String v5, String v6, String v7, String v8, String v9,
			String v10)
	{
		this.variableNames.add(v1);
		this.variableNames.add(v2);
		this.variableNames.add(v3);
		this.variableNames.add(v4);
		this.variableNames.add(v5);
		this.variableNames.add(v6);
		this.variableNames.add(v7);
		this.variableNames.add(v8);
		this.variableNames.add(v9);
		this.variableNames.add(v10);
	}

	public List<String> getVariableNames()
	{
		return this.variableNames;
	}

	public int getNumberOfArguments()
	{
		return this.variableNames.size();
	}

	public boolean hasVariableNames()
	{
		return getNumberOfArguments() != 0;
	}

	@Override
	public String toString()
	{
		String representation = "BAVNs(";
		boolean isFirst = true;

		for (String variableName : this.variableNames) {
			if (!isFirst)
				representation += ", ";
			else
				isFirst = false;
			representation += variableName;
		}
		representation += ")";
		return representation;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		BAVNs avns = (BAVNs)obj;
		return (getVariableNames() == avns.getVariableNames() || (getVariableNames() != null && getVariableNames().equals(
				avns.getVariableNames())));
	}

	@Override
	public int hashCode()
	{
		int hash = 66;

		hash = hash + (null == getVariableNames() ? 0 : getVariableNames().hashCode());

		return hash;
	}
}
