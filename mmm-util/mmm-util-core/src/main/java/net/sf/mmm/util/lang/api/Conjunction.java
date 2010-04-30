/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A conjunction represents a function that maps a list of boolean arguments to
 * one boolean result.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Conjunction implements Datatype<String> {
  /**
   * This conjunction is <code>true</code> if and only if all arguments are
   * <code>true</code>.
   */
  AND("&&", NlsBundleUtilCore.INF_AND) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(boolean... arguments) {

      for (boolean b : arguments) {
        if (!b) {
          return false;
        }
      }
      return true;
    }
  },

  /**
   * This conjunction is <code>true</code> if and only if at least one argument
   * is <code>true</code>.
   */
  OR("||", NlsBundleUtilCore.INF_OR) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(boolean... arguments) {

      for (boolean b : arguments) {
        if (b) {
          return true;
        }
      }
      return false;
    }
  },

  /**
   * This is the negation of {@link #AND}. It is only <code>true</code> if at
   * least one argument is <code>false</code>.
   */
  NAND("!&", NlsBundleUtilCore.INF_NAND) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(boolean... arguments) {

      for (boolean b : arguments) {
        if (!b) {
          return true;
        }
      }
      return false;
    }
  },

  /**
   * This is the negation of {@link #OR}. It is only <code>true</code> if all
   * arguments are <code>false</code>.
   */
  NOR("!|", NlsBundleUtilCore.INF_NOR) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(boolean... arguments) {

      for (boolean b : arguments) {
        if (b) {
          return false;
        }
      }
      return true;
    }
  };

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #getTitle() title}.
   */
  private Conjunction(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * This method evaluates this conjunction for the given boolean
   * <code>arguments</code>.
   * 
   * @param arguments are the boolean values to evaluate.
   * @return the result of this conjunction applied to the given
   *         <code>arguments</code>.
   * @since 2.0.0
   */
  public abstract boolean eval(boolean... arguments);

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

  /**
   * This method gets the {@link Conjunction} with the given
   * <code>{@link #getValue() value}</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link Conjunction}.
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
