/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.base;

import net.sf.mmm.data.reflection.api.ContentReflectionException;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.data.reflection.api.ContentModifiers Modifiers} were created
 * with an illegal combination of flags.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentModifiersIllegalException extends ContentReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -9096716721784689821L;

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   */
  public ContentModifiersIllegalException(String internaitionalizedMessage) {

    super(internaitionalizedMessage);
  }

}
