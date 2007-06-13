/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

/**
 * This class partially implements a binary function that builds the sum of its
 * arguments. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctAddString extends FctAdd {

  /**
   * The constructor.
   */
  private FctAddString() {

    super();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static String add(String argument1, Object argument2) {

    return argument1 + argument2;
  }

}
