package org.swrlapi.drools.sqwrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.swrlapi.drools.swrl.BA;

/**
 * Drools has issues with some varargs so we need to explicitly define the various argument combinations.
 */
public class VPATH
{
	public static final int MaxArguments = 10;

	private final List<BA> arguments;

	public VPATH()
	{
		this.arguments = new ArrayList<BA>();
	}

	public VPATH(BA ba1)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
	}

	public VPATH(BA ba1, BA ba2)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
	}

	public VPATH(BA ba1, BA ba2, BA ba3)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
		this.arguments.add(ba6);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
		this.arguments.add(ba6);
		this.arguments.add(ba7);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
		this.arguments.add(ba6);
		this.arguments.add(ba7);
		this.arguments.add(ba8);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
		this.arguments.add(ba6);
		this.arguments.add(ba7);
		this.arguments.add(ba8);
		this.arguments.add(ba9);
	}

	public VPATH(BA ba1, BA ba2, BA ba3, BA ba4, BA ba5, BA ba6, BA ba7, BA ba8, BA ba9, BA ba10)
	{
		this.arguments = new ArrayList<BA>();
		this.arguments.add(ba1);
		this.arguments.add(ba2);
		this.arguments.add(ba3);
		this.arguments.add(ba4);
		this.arguments.add(ba5);
		this.arguments.add(ba6);
		this.arguments.add(ba7);
		this.arguments.add(ba8);
		this.arguments.add(ba9);
		this.arguments.add(ba10);
	}

	public List<BA> getArguments()
	{
		return Collections.unmodifiableList(this.arguments);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (BA ba : arguments)
			sb.append(ba.toString() + ":");

		return sb.toString();
	}
}
