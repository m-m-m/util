/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the abstract interface for any container of content resources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentObjectReadAccess {

  /**
   * This method gets the {@link ContentObject} with the given <code>id</code>.
   * 
   * @param id is the unique identifier of the requested object.
   * @return the object with the given identifier.
   * @throws ContentException if no object exists with the given ID.
   */
  ContentObject get(ContentId id) throws ContentException;

  /**
   * This method gets the {@link ContentObject} with the given
   * <code>{@link ContentObject#getPath() path}</code>.
   * 
   * @param path is the {@link ContentObject#getPath() path} of the requested
   *        object.
   * @return the object with the given <code>path</code>.
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the path is illegal.</li>
   *         <li>no resource exists for the given <code>path</code>.</li>
   *         </ul>
   */
  ContentObject get(String path) throws ContentException;

}
