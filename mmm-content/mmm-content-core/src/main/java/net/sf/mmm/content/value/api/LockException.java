/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import net.sf.mmm.util.nls.api.NlsRuntimeException;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LockException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5565730016992127038L;

  /**
   * The constructor.
   *
   * @param internaitionalizedMessage
   * @param arguments
   */
  public LockException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor.
   *
   * @param nested
   * @param internaitionalizedMessage
   * @param arguments
   */
  public LockException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }
  
}
