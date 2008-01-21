/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.util.value.ValueException;

/**
 * This is the exception thrown if the
 * {@link net.sf.mmm.term.api.Term#evaluate(net.sf.mmm.context.api.Context) evaluation}
 * of a {@link net.sf.mmm.term.api.Term term} failes because of an invalid
 * calculation. This can have various reasons such as zero divide or
 * incompatible types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CalculationException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 8736558379218781121L;

  /**
   * @see ValueException#ValueException(String, Object[])
   */
  public CalculationException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see ValueException#ValueException(Throwable, String, Object[])
   */
  public CalculationException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
