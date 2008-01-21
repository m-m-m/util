/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.NlsBundleContentCore;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.content.model.api.ContentField field} could NOT be
 * registered because a field with the same
 * {@link net.sf.mmm.content.api.ContentObject#getName() name} or
 * {@link net.sf.mmm.content.api.ContentObject#getId() ID} already exists.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateFieldException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6393217248604007769L;

  /**
   * The constructor.
   * 
   * @param name is the name already in use.
   */
  public DuplicateFieldException(String name) {

    super(NlsBundleContentCore.ERR_DUPLICATE_FIELD_NAME, name);
  }

  /**
   * The constructor.
   * 
   * @param id is the ID already in use.
   */
  public DuplicateFieldException(ContentId id) {

    super(NlsBundleContentCore.ERR_DUPLICATE_FIELD_ID, id);
  }

}
