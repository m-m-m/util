/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api.access;

import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;

/**
 * This interface allows to {@link #getContentClass(ContentId) get} a
 * {@link ContentClass} by its {@link ContentClass#getContentId() ID}.
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentClassReadAccessById<CLASS> {

  /**
   * This method gets the {@link ContentClass} for the given <code>id</code>.
   * 
   * @see ContentId#getContentClassId()
   * @see net.sf.mmm.content.api.ContentIdManager#getClassId(int)
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  ContentClass<? extends CLASS> getContentClass(ContentId id);

}
