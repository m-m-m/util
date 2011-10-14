/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;

/**
 * This interface allows to {@link #getContentClass(long) get} a
 * {@link DataClass} by its {@link DataClass#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassReadAccessById {

  /**
   * This method gets the {@link DataClass} for the given <code>id</code>.
   * 
   * @see DataClass#getId()
   * @see net.sf.mmm.data.api.datatype.DataId#getContentClassId()
   * @see net.sf.mmm.data.api.DataIdManager#getClassId(int)
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  DataClass<? extends DataObject> getContentClass(long id);

}
