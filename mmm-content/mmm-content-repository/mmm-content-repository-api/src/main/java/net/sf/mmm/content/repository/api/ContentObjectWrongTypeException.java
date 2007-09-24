/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.repository.NlsBundleContentRepository;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.content.api.ContentObject content-object} was requested but
 * does NOT have the requested
 * {@link net.sf.mmm.content.api.ContentObject#getContentClass() type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentObjectWrongTypeException extends ContentException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6798775723820144842L;

  /**
   * The constructor.
   * 
   * @param objectInfo
   * @param objectType
   * @param expectedType
   */
  public ContentObjectWrongTypeException(String objectInfo, String objectType, String expectedType) {

    super(NlsBundleContentRepository.ERR_OBJECT_WRONG_TYPE, objectInfo, objectType, expectedType);
  }

  /**
   * The constructor.
   * 
   * @param object
   * @param expectedType
   */
  public ContentObjectWrongTypeException(ContentObject object, String expectedType) {

    super(NlsBundleContentRepository.ERR_OBJECT_WRONG_TYPE, object, object.getContentClass(),
        expectedType);
  }

  /**
   * The constructor.
   * 
   * @param nested 
   * @param object
   * @param expectedType
   */
  public ContentObjectWrongTypeException(Throwable nested, ContentObject object, String expectedType) {

    super(nested, NlsBundleContentRepository.ERR_OBJECT_WRONG_TYPE, object, object
        .getContentClass(), expectedType);
  }

}
