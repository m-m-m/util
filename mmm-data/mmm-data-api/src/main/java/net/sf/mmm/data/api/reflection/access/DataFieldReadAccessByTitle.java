/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataField(String) get} a {@link DataField} by its
 * {@link DataField#getTitle() title}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldReadAccessByTitle {

  /**
   * This method gets the {@link DataField} for the given <code>title</code>.
   * 
   * @param title is the {@link DataField#getTitle() title} of the requested field.
   * @return the content field for the given <code>title</code>.
   * @throws ObjectNotFoundException if the requested {@link DataField} does NOT exist.
   */
  DataField<? extends DataObject, ?> getDataField(String title) throws ObjectNotFoundException;

}
