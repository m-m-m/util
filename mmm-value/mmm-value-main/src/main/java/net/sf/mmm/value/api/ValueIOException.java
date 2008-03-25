/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import net.sf.mmm.util.value.api.ValueException;

/**
 * This exception is thrown if a value caused an input-/output- (IO) error.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueIOException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = -473327579096235521L;

  /**
   * @see ValueException#ValueException(String, Object[])
   */
  public ValueIOException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see ValueException#ValueException(Throwable, String, Object[])
   */
  public ValueIOException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
