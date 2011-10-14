/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataField;

/**
 * This interface allows to {@link #getContentField(String) get} a
 * {@link DataField} by its {@link DataField#getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldReadAccessByName {

  /**
   * This method gets the {@link DataField} for the given <code>id</code>.
   * 
   * @param name is the {@link DataField#getTitle() name} of the requested
   *        field.
   * @return the content field for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  DataField<? extends DataObject, ?> getContentField(String name);

}
