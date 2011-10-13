/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import net.sf.mmm.content.NlsBundleContentApi;
import net.sf.mmm.content.reflection.api.ContentReflectionException;

/**
 * This exception is thrown if a {@link ContentObject} could NOT be modified
 * because it is
 * {@link net.sf.mmm.content.reflection.api.ContentModifiers#isSystem() required by
 * the system}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentModelSystemModifyException extends ContentReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5661107156513140887L;

  /**
   * The constructor.
   * 
   * @param systemObject is the {@link ContentObject} that could NOT be modified
   *        because it is a
   *        {@link net.sf.mmm.content.reflection.api.ContentModifiers#isSystem()
   *        system}-object.
   */
  public ContentModelSystemModifyException(ContentObject systemObject) {

    super(NlsBundleContentApi.ERR_MODIFY_SYSTEM, toMap(KEY_OBJECT, systemObject));
  }
}
