/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * A conjunction represents a function that maps a list of boolean arguments to
 * one boolean result.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Conjunction {
  /**
   * This conjunction is <code>true</code> if and only if all arguments are
   * <code>true</code>.
   */
  AND {

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
  OR {

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
  NAND {

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
  NOR {

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

  /**
   * This method evaluates this conjunction for the given boolean
   * <code>arguments</code>.
   * 
   * @param arguments are the boolean values to evaluate.
   * @return the result of this conjunction applied to the given
   *         <code>arguments</code>.
   * @since 1.1.2
   */
  public abstract boolean eval(boolean... arguments);
  // {
  //
  // for (boolean b : arguments) {
  // switch (this) {
  // case OR:
  // if (b) {
  // return true;
  // }
  // break;
  // case AND:
  // if (!b) {
  // return false;
  // }
  // break;
  // case NOR:
  // if (b) {
  // return false;
  // }
  // break;
  // case NAND:
  // if (!b) {
  // return true;
  // }
  // break;
  // default :
  // throw new IllegalCaseException(Conjunction.class, this);
  // }
  // }
  // switch (this) {
  // case OR:
  // case NAND:
  // return false;
  // case AND:
  // case NOR:
  // return true;
  // default :
  // throw new IllegalCaseException(Conjunction.class, this);
  // }
  // }
}
