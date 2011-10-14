/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataField;

/**
 * This interface allows to {@link #getContentField(long) get} a
 * {@link DataField} by its {@link DataField#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldReadAccessById {

  /**
   * This method gets the {@link DataField} for the given <code>id</code>.
   * 
   * @see DataField#getId()
   * @see net.sf.mmm.data.api.DataIdManager#getFieldId(int)
   * 
   * @param id is the unique ID of the requested class.
   * @return the content field for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  DataField<? extends DataObject, ?> getContentField(long id);

}
