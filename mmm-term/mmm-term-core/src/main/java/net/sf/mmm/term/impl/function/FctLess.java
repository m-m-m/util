/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import java.util.Date;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that determines if the
 * first argument is less than the second. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctLess {

  /**
   * the
   * {@link net.sf.mmm.term.api.Function#getOperatorSymbol() "operator symbol"}
   */
  public static final String SYMBOL = "<";

  /**
   * the {@link net.sf.mmm.term.api.Function#getName() name} of this function
   */
  public static final String NAME = "less";

  /**
   * the {@link net.sf.mmm.term.api.Function#getOperatorPriority() priority} of
   * this function
   */
  public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

  /**
   * The constructor.
   */
  private FctLess() {

    super();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return <code>true</code> if the first argument is less than the second.
   */
  public static Boolean less(Number argument1, Number argument2) {

    return Boolean.valueOf(argument1.doubleValue() < argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return <code>true</code> if the first argument is after the second.
   */
  public static Boolean less(Date argument1, Date argument2) {

    return Boolean.valueOf(argument1.before(argument2));
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return <code>true</code> if the first argument is before the second.
   */
  public static Boolean less(String argument1, String argument2) {

    int compare = argument1.compareTo(argument2);
    return Boolean.valueOf(compare < 0);
  }

}
