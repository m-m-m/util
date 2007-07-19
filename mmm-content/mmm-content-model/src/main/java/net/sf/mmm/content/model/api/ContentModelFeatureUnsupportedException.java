/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

/**
 * This is the exception thrown if a feature of the content model was invoked
 * that is not supported.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelFeatureUnsupportedException extends ContentModelException {

  /** uid for serialization */
  private static final long serialVersionUID = 3258689914253423670L;

  /**
   * @see ContentModelException#ContentModelException(String, Object[])
   */
  public ContentModelFeatureUnsupportedException(String internaitionalizedMessage,
      Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see ContentModelException#ContentModelException(Throwable, String, Object[])
   */
  public ContentModelFeatureUnsupportedException(Throwable nested,
      String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
