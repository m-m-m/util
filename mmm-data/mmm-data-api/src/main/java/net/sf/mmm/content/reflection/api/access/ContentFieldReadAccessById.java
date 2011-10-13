/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api.access;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentField;

/**
 * This interface allows to {@link #getContentField(long) get} a
 * {@link ContentField} by its {@link ContentField#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentFieldReadAccessById {

  /**
   * This method gets the {@link ContentField} for the given <code>id</code>.
   * 
   * @see ContentField#getId()
   * @see net.sf.mmm.content.api.ContentIdManager#getFieldId(int)
   * 
   * @param id is the unique ID of the requested class.
   * @return the content field for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  ContentField<? extends ContentObject, ?> getContentField(long id);

}
