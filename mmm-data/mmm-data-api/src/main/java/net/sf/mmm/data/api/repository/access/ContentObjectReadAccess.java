/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.repository.access;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;

/**
 * This is the abstract interface for any container of content resources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentObjectReadAccess extends ContentObjectReadAccessById {

  /**
   * This method gets the {@link DataObject} with the given
   * <code>{@link DataObject#getPath() path}</code>.
   * 
   * @param path is the {@link DataObject#getPath() path} of the requested
   *        object.
   * @return the object with the given <code>path</code>.
   * @throws DataException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the path is illegal.</li>
   *         <li>no resource exists for the given <code>path</code>.</li>
   *         </ul>
   */
  DataObject get(String path) throws DataException;

}
