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
  AND,

  /**
   * This conjunction is <code>true</code> if and only if at least one argument
   * is <code>true</code>.
   */
  OR,

  /**
   * This is the negation of {@link #AND}. It is only <code>true</code> if at
   * least one argument is <code>false</code>.
   */
  NAND,

  /**
   * This is the negation of {@link #OR}. It is only <code>true</code> if all
   * arguments are <code>false</code>.
   */
  NOR
}
