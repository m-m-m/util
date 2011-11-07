/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataClass(long) get} a {@link DataClass}
 * by its {@link DataClass#getId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassReadAccessById {

  /**
   * This method gets the {@link DataClass} for the given <code>id</code>.
   * 
   * @see DataClass#getId()
   * @see net.sf.mmm.data.api.datatype.DataId#getDataClassId()
   * @see net.sf.mmm.data.api.DataIdManager#getClassId(long)
   * 
   * @param id is the {@link DataClass#getId() unique ID} of the requested
   *        class.
   * @return the content class for the given <code>id</code>.
   * @throws ObjectNotFoundException if the requested {@link DataClass} does NOT
   *         exist.
   */
  DataClass<? extends DataObject> getDataClass(long id) throws ObjectNotFoundException;

  /**
   * This method gets the {@link DataClass} for the given <code>id</code>.
   * 
   * @see DataClass#getId()
   * @see net.sf.mmm.data.api.datatype.DataId#getDataClassId()
   * @see net.sf.mmm.data.api.DataIdManager#getClassId(long)
   * 
   * @param id is the {@link DataReflectionReadAccess#getDataId(DataObject)
   *        unique ID} of the requested class.
   * @return the content class for the given <code>id</code>.
   * @throws ObjectNotFoundException if the requested {@link DataClass} does NOT
   *         exist.
   */
  DataClass<? extends DataObject> getDataClass(DataId id) throws ObjectNotFoundException;

  // DataClass<? extends DataObject> getDataClass(long id, String name) {

}
