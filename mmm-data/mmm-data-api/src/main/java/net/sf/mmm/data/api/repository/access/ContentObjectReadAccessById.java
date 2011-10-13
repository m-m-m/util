/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.repository.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #get(DataId) get} a {@link DataObject}
 * by its {@link DataObject#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentObjectReadAccessById {

  /**
   * This method gets the {@link DataObject}
   * {@link DataObject#getContentId() with} the given <code>id</code>.
   * 
   * @param id is the {@link DataId} of the requested {@link DataObject}.
   * @return the object with the given identifier.
   * @throws ObjectNotFoundException if no object exists with the given
   *         <code>id</code>.
   */
  DataObject get(DataId id) throws ObjectNotFoundException;

}
