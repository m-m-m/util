/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import java.util.List;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;

/**
 * This interface gives read access to the content-model (reflection).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataReflectionReadAccess extends DataClassReadAccessById,
    DataClassReadAccessByTitle, DataClassReadAccessByInstance, DataFieldReadAccessById,
    DataFieldReadAccessByName {

  /**
   * This method gets the root {@link DataClass} that reflects the
   * {@link net.sf.mmm.data.api.DataObject content-object}.
   * 
   * @return the root class.
   */
  DataClass<? extends DataObject> getRootDataClass();

  /**
   * This method gets the list of all registered {@link DataClass} objects.
   * 
   * @return the immutable list of all classes.
   */
  List<? extends DataClass<? extends DataObject>> getDataClasses();

  /**
   * This method gets the {@link DataId} for the given <code>dataObject</code>.
   * 
   * @param dataObject is the {@link DataObject} for which the {@link DataId} is
   *        requested.
   * @return the {@link DataId} of the given <code>dataObject</code>.
   */
  DataId getDataId(DataObject dataObject);

}
