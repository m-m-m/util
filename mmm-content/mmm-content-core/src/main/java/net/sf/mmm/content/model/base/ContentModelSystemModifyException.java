/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.NlsBundleContentCore;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentReflectionObject;

/**
 * This exception is thrown if a {@link ContentReflectionObject} could NOT be
 * modified because it is
 * {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}-object
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelSystemModifyException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5661107156513140887L;

  /**
   * The constructor.
   * 
   * @param systemObject is the {@link ContentReflectionObject} that could NOT
   *        be modified because it is a
   *        {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}-object.
   */
  public ContentModelSystemModifyException(ContentReflectionObject systemObject) {

    super(NlsBundleContentCore.ERR_MODIFY_SYSTEM, systemObject);
  }
}
