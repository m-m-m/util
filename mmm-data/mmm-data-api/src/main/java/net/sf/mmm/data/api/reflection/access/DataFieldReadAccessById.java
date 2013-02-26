/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataField(long) get} a {@link DataField} by its
 * {@link DataField#getId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldReadAccessById {

  /**
   * This method gets the {@link DataField} for the given <code>id</code>.
   * 
   * @see DataField#getId()
   * @see net.sf.mmm.data.api.DataIdManager#getFieldId(long)
   * 
   * @param id is the unique ID of the requested {@link DataField}.
   * @return the content field for the given <code>id</code>.
   * @throws ObjectNotFoundException if the requested {@link DataField} does NOT exist.
   */
  DataField<? extends DataObject, ?> getDataField(long id) throws ObjectNotFoundException;

  /**
   * This method gets the {@link DataField} for the given <code>id</code>.
   * 
   * @see DataField#getId()
   * @see net.sf.mmm.data.api.DataIdManager#getFieldId(long)
   * 
   * @param id is the unique {@link DataId} of the requested {@link DataField}.
   * @return the content field for the given <code>id</code>.
   * @throws ObjectNotFoundException if the requested {@link DataField} does NOT exist.
   */
  DataField<? extends DataObject, ?> getDataField(DataId id) throws ObjectNotFoundException;

}
