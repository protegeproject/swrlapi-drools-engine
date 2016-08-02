package org.swrlapi.drools.swrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent SWRL built-in argument pattern. Built-ins that evaluate to true return a list of BAPs
 * representing all possible valid argument patterns.
 *
 * @see org.swrlapi.drools.swrl.BA
 */
public class BAP
{
  public static final int MaxArguments = 11;

  // This are used indirectly by Drools. The actual arguments are placed in this.arguments.
  // This approach is not great but it will do for the moment.
  @SuppressWarnings("unused") @Nullable private BA a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11;

  @NonNull private final List<@NonNull BA> arguments;

  public BAP()
  {
    this.arguments = new ArrayList<>();
  }

  public void addArgument(@NonNull BA argument)
  {
    this.arguments.add(argument);
    setArgument(this.arguments.size(), argument);
  }

  private void setArgument(int argumentNumber, @NonNull BA argument)
  {
    if (argumentNumber == 1)
      this.a1 = argument;
    else if (argumentNumber == 2)
      this.a2 = argument;
    else if (argumentNumber == 3)
      this.a3 = argument;
    else if (argumentNumber == 4)
      this.a4 = argument;
    else if (argumentNumber == 5)
      this.a5 = argument;
    else if (argumentNumber == 6)
      this.a6 = argument;
    else if (argumentNumber == 7)
      this.a7 = argument;
    else if (argumentNumber == 8)
      this.a8 = argument;
    else if (argumentNumber == 9)
      this.a9 = argument;
    else if (argumentNumber == 10)
      this.a10 = argument;
    else if (argumentNumber == 11)
      this.a11 = argument;
    else
      throw new TargetSWRLRuleEngineInternalException("argument number " + argumentNumber + " out of bounds");
  }

  @NonNull public BA getArgument(int argumentNumber)
  {
    if (argumentNumber > 0 && this.arguments.size() > argumentNumber)
      return this.arguments.get(argumentNumber - 1);
    else
      throw new TargetSWRLRuleEngineInternalException("argument number " + argumentNumber + " out of bounds");
  }

  @NonNull public BA getA1()
  {
    if (this.arguments.size() < 1)
      throwInvalidArgumentNumberException(1);

    return this.arguments.get(0);
  }

  @NonNull public BA getA2()
  {
    if (this.arguments.size() < 2)
      throwInvalidArgumentNumberException(2);

    return this.arguments.get(1);
  }

  @NonNull public BA getA3()
  {
    if (this.arguments.size() < 3)
      throwInvalidArgumentNumberException(3);

    return this.arguments.get(2);
  }

  @NonNull public BA getA4()
  {
    if (this.arguments.size() < 4)
      throwInvalidArgumentNumberException(4);

    return this.arguments.get(3);
  }

  @NonNull public BA getA5()
  {
    if (this.arguments.size() < 5)
      throwInvalidArgumentNumberException(5);

    return this.arguments.get(4);
  }

  @NonNull public BA getA6()
  {
    if (this.arguments.size() < 6)
      throwInvalidArgumentNumberException(6);

    return this.arguments.get(5);
  }

  @NonNull public BA getA7()
  {
    if (this.arguments.size() < 7)
      throwInvalidArgumentNumberException(7);

    return this.arguments.get(6);
  }

  @NonNull public BA getA8()
  {
    if (this.arguments.size() < 8)
      throwInvalidArgumentNumberException(8);

    return this.arguments.get(7);
  }

  @NonNull public BA getA9()
  {
    if (this.arguments.size() < 9)
      throwInvalidArgumentNumberException(9);

    return this.arguments.get(8);
  }

  @NonNull public BA getA10()
  {
    if (this.arguments.size() < 10)
      throwInvalidArgumentNumberException(10);

    return this.arguments.get(9);
  }

  @NonNull public BA getA11()
  {
    if (this.arguments.size() < 11)
      throwInvalidArgumentNumberException(11);

    return this.arguments.get(10);
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return this.arguments.toString();
  }

  private void throwInvalidArgumentNumberException(int argumentNumber)
  {
    throw new TargetSWRLRuleEngineInternalException(
      "argument number " + argumentNumber + " out of bounds; current number of arguments = " + this.arguments.size());
  }
}
