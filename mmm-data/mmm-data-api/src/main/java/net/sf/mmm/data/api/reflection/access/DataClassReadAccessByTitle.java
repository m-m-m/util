/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataClass(String) get} a
 * {@link DataClass} by its {@link DataClass#getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassReadAccessByTitle {

  /**
   * This method gets the content class for the given name.
   * 
   * @param title is the {@link DataClass#getTitle() title} of the requested
   *        class.
   * @return the {@link DataClass} for the given name.
   * @throws ObjectNotFoundException if the requested {@link DataClass} does NOT
   *         exist.
   */
  DataClass<? extends DataObject> getDataClass(String title) throws ObjectNotFoundException;

}
