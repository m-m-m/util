/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception thrown if an internal error occurred in the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelRuntimeException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 4743655362572569172L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   */
  public ContentModelRuntimeException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   */
  public ContentModelRuntimeException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
