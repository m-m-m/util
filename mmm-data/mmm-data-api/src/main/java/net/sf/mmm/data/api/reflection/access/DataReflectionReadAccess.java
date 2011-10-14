/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import java.util.List;

import net.sf.mmm.data.api.DataObject;
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
   * This method gets the root content class that reflects the
   * {@link net.sf.mmm.data.api.DataObject content-object}.
   * 
   * @return the root class.
   */
  DataClass<? extends DataObject> getRootContentClass();

  /**
   * This method gets the list of all registered content classes.
   * 
   * @return the immutable list of all content classes.
   */
  List<? extends DataClass<? extends DataObject>> getContentClasses();

}
