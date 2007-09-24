/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.repository.NlsBundleContentRepository;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.content.api.ContentObject content-object} was requested
 * that does NOT exists.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentObjectNotExistsException extends ContentException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1934806135294064548L;

  /**
   * The constructor.
   * 
   * @param id is the {@link ContentId ID} of the requested
   *        {@link net.sf.mmm.content.api.ContentObject} that does NOT exists.
   */
  public ContentObjectNotExistsException(ContentId id) {

    super(NlsBundleContentRepository.ERR_OBJECT_NOT_EXISTS_FOR_ID, id);
  }

  /**
   * The constructor.
   * 
   * @param path is the
   *        {@link net.sf.mmm.content.api.ContentObject#getPath() path} of the
   *        requested {@link net.sf.mmm.content.api.ContentObject} that does NOT
   *        exists.
   */
  public ContentObjectNotExistsException(String path) {

    super(NlsBundleContentRepository.ERR_OBJECT_NOT_EXISTS_FOR_PATH, path);
  }

}
