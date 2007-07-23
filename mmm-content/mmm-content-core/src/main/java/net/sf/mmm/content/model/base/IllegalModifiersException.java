/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.model.api.ContentModelException;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.content.model.api.Modifiers Modifiers} were created with an
 * illegal combination of flags.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalModifiersException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = -9096716721784689821L;

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   */
  public IllegalModifiersException(String internaitionalizedMessage) {

    super(internaitionalizedMessage);
  }

}
