/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentException;

/**
 * This exception is used if something went wrong with the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelException extends ContentException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3544669568611071536L;

  /**
   * @see ContentException#ContentException(String, Object[])
   */
  public ContentModelException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see ContentException#ContentException(Throwable, String, Object[])
   */
  public ContentModelException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
