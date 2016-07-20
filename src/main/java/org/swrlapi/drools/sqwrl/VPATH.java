package org.swrlapi.drools.sqwrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.drools.swrl.BA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Drools has issues with some varargs so we need to explicitly define constructors the various argument combinations.
 */
public class VPATH
{
  public static final int MaxArguments = 10;

  @NonNull private final List<@NonNull BA> arguments;

  public VPATH()
  {
    this.arguments = new ArrayList<>();
  }

  public VPATH(@NonNull BA ba1)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
    this.arguments.add(ba4);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
    this.arguments.add(ba4);
    this.arguments.add(ba5);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
    this.arguments.add(ba4);
    this.arguments.add(ba5);
    this.arguments.add(ba6);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6,
    @NonNull BA ba7)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
    this.arguments.add(ba4);
    this.arguments.add(ba5);
    this.arguments.add(ba6);
    this.arguments.add(ba7);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6,
    @NonNull BA ba7, @NonNull BA ba8)
  {
    this.arguments = new ArrayList<>();
    this.arguments.add(ba1);
    this.arguments.add(ba2);
    this.arguments.add(ba3);
    this.arguments.add(ba4);
    this.arguments.add(ba5);
    this.arguments.add(ba6);
    this.arguments.add(ba7);
    this.arguments.add(ba8);
  }

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6,
    @NonNull BA ba7, @NonNull BA ba8, @NonNull BA ba9)
  {
    this.arguments = new ArrayList<>();
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

  public VPATH(@NonNull BA ba1, @NonNull BA ba2, @NonNull BA ba3, @NonNull BA ba4, @NonNull BA ba5, @NonNull BA ba6,
    @NonNull BA ba7, @NonNull BA ba8, @NonNull BA ba9, @NonNull BA ba10)
  {
    this.arguments = new ArrayList<>();
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

  @NonNull public List<@NonNull BA> getArguments()
  {
    return Collections.unmodifiableList(this.arguments);
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();

    for (BA ba : this.arguments)
      sb.append(ba.toString()).append(":");

    return sb.toString();
  }
}
