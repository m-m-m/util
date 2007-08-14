/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface used to {@link #get(ContentObject) read} the
 * {@link ContentField field} of a {@link ContentObject content-object}.
 * 
 * @see ContentField#getAccessor()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentReadAccessor {

  /**
   * This method reads the value of the field from the given <code>object</code>.
   * 
   * @param object is where to read the fields value from.
   * @return the value of the field. May be <code>null</code>.
   * @throws ContentException if the operation fails.
   */
  Object get(ContentObject object) throws ContentException;

}
