/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link Conjunction} represents a function that maps a list of boolean arguments to one boolean result.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Conjunction implements EnumType<String> {
  /**
   * This conjunction is <code>true</code> if and only if all arguments are <code>true</code>.
   */
  AND("&&", NlsBundleUtilCoreRoot.INF_AND) {

    @Override
    public boolean evalEmpty() {

      return true;
    }

    @Override
    public Boolean evalSingle(boolean argument) {

      if (!argument) {
        return Boolean.FALSE;
      }
      return null;
    }

    @Override
    public Conjunction negate() {

      return NAND;
    }
  },

  /**
   * This conjunction is <code>true</code> if and only if at least one argument is <code>true</code>.
   */
  OR("||", NlsBundleUtilCoreRoot.INF_OR) {

    @Override
    public boolean evalEmpty() {

      return false;
    }

    @Override
    public Boolean evalSingle(boolean argument) {

      if (argument) {
        return Boolean.TRUE;
      }
      return null;
    }

    @Override
    public Conjunction negate() {

      return NOR;
    }
  },

  /**
   * This is the negation of {@link #AND}. It is only <code>true</code> if at least one argument is <code>false</code>.
   */
  NAND("!&", NlsBundleUtilCoreRoot.INF_NAND) {

    @Override
    public boolean evalEmpty() {

      return false;
    }

    @Override
    public Boolean evalSingle(boolean argument) {

      if (!argument) {
        return Boolean.TRUE;
      }
      return null;
    }

    @Override
    public Conjunction negate() {

      return AND;
    }
  },

  /**
   * This is the negation of {@link #OR}. It is only <code>true</code> if all arguments are <code>false</code> .
   */
  NOR("!|", NlsBundleUtilCoreRoot.INF_NOR) {

    @Override
    public boolean evalEmpty() {

      return true;
    }

    @Override
    public Boolean evalSingle(boolean argument) {

      if (argument) {
        return Boolean.FALSE;
      }
      return null;
    }

    @Override
    public Conjunction negate() {

      return OR;
    }
  };

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string representation}.
   */
  private Conjunction(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * This method evaluates this conjunction for the given boolean <code>arguments</code>.
   *
   * @param arguments are the boolean values to evaluate.
   * @return the result of this conjunction applied to the given <code>arguments</code>.
   * @since 2.0.0
   */
  public final boolean eval(boolean... arguments) {

    for (boolean b : arguments) {
      Boolean single = evalSingle(b);
      if (single != null) {
        return single.booleanValue();
      }
    }
    return evalEmpty();
  }

  /**
   * @since 7.1.0
   * @param argument is a literal boolean argument.
   * @return {@link Boolean#TRUE} if {@link #eval(boolean...)} will return {@code true} if any of the given arguments
   *         has the value of the given {@code argument}, {@link Boolean#FALSE} if {@link #eval(boolean...)} will return
   *         {@code false} if any of the given arguments has the value of the given {@code argument}, {@code null}
   *         otherwise.
   */
  public abstract Boolean evalSingle(boolean argument);

  /**
   * @since 7.1.0
   * @return the result of {@link #eval(boolean...)} for no argument (an empty argument array).
   */
  public abstract boolean evalEmpty();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * @return <code>true</code> if {@link #NOR} or {@link #NAND}, <code>false</code> otherwise.
   * @since 3.0.0
   */
  public boolean isNegation() {

    return ((this == NOR) || (this == NAND));
  }

  /**
   * @since 7.1.0
   * @return the negation of this {@link Conjunction} that {@link #eval(boolean...) evaluates} to the negated result.
   */
  public abstract Conjunction negate();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link Conjunction} with the given <code>{@link #getValue() value}</code>.
   *
   * @param value is the {@link #getValue() value} of the requested {@link Conjunction}.
   * @return the requested {@link Conjunction}.
   */
  public static Conjunction fromValue(String value) {

    for (Conjunction alignment : values()) {
      if (alignment.value.equals(value)) {
        return alignment;
      }
    }
    return null;
  }
}
