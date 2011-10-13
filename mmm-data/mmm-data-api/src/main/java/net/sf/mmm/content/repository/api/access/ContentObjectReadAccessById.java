/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api.access;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #get(ContentId) get} a {@link ContentObject}
 * by its {@link ContentObject#getContentId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentObjectReadAccessById {

  /**
   * This method gets the {@link ContentObject}
   * {@link ContentObject#getContentId() with} the given <code>id</code>.
   * 
   * @param id is the {@link ContentId} of the requested {@link ContentObject}.
   * @return the object with the given identifier.
   * @throws ObjectNotFoundException if no object exists with the given
   *         <code>id</code>.
   */
  ContentObject get(ContentId id) throws ObjectNotFoundException;

}
