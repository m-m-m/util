/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api.access;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This interface allows to {@link #getContentClass(ContentId) get} a
 * {@link ContentClass} by its {@link ContentClass#getId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassReadAccessById {

  /**
   * This method gets the {@link ContentClass} for the given <code>id</code>.
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it
   *         does NOT exist.
   */
  ContentClass getContentClass(ContentId id);

}
