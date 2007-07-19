/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the abstract interface for any container of content resources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentResourceReadAccess {

  /**
   * This method gets the resource with the given identifier.
   * 
   * @param id is the unique identifier of the requested resource.
   * @return the resource with the given identifier.
   * @throws ContentException if no resource exists for the given ID.
   */
  ContentResource getResource(ContentId id) throws ContentException;

  /**
   * This method gets the resource with the given URI. The URI in this context
   * is the absolute path of the resource relative to this container (the
   * current store or the repository).
   * 
   * @param uri is the URI of the requested resource.
   * @return the resource with the given URI.
   * @throws ContentException if the operation faild. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the URI-format is illegal.</li>
   *         <li>no resource exists for the given URI.</li>
   *         </ul>
   */
  ContentResource getResource(String uri) throws ContentException;

}
