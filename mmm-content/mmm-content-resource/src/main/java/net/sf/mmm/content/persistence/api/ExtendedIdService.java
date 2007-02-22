/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.persistence.api;

import net.sf.mmm.content.persistence.api.IdService;
import net.sf.mmm.content.value.api.Id;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ExtendedIdService extends IdService {

  /**
   * This method creates a unique identifier for a new
   * {@link net.sf.mmm.content.resource.api.ContentResource#getRevision() revision}
   * of a
   * {@link net.sf.mmm.content.resource.api.ContentResource content-resource}.
   * 
   * @param existingId
   *        is the {@link net.sf.mmm.content.api.ContentObject#getId() ID} of
   *        the existing
   *        {@link net.sf.mmm.content.resource.api.ContentResource content-resource}.
   * @return the new unique identifier.
   */
  Id createRevisionId(Id existingId);

}
