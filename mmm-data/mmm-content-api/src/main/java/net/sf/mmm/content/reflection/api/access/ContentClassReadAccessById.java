/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api.access;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentClass;

/**
 * This interface allows to {@link #getContentClass(long) get} a
 * {@link ContentClass} by its {@link ContentClass#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentClassReadAccessById {

  /**
   * This method gets the {@link ContentClass} for the given <code>id</code>.
   * 
   * @see ContentClass#getId()
   * @see net.sf.mmm.content.datatype.api.ContentId#getContentClassId()
   * @see net.sf.mmm.content.api.ContentIdManager#getClassId(int)
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  ContentClass<? extends ContentObject> getContentClass(long id);

}
