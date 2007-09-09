/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.value.api.ContentId;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SimpleContentModelReadAccess {

  /**
   * This method gets the content class for the given name.
   * 
   * @param name is the name of the requested class.
   * @return the content class for the given name or <code>null</code> if it
   *         does NOT exist.
   */
  ContentClass getContentClass(String name);

  /**
   * This method gets the {@link ContentClass} for the given <code>id</code>.
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it
   *         does NOT exist.
   */
  ContentClass getContentClass(ContentId id);

  /**
   * This method gets the {@link ContentField} for the given <code>id</code>.
   * 
   * @param id is the unique ID of the requested class.
   * @return the content field for the given ID or <code>null</code> if it
   *         does NOT exist.
   */
  ContentField getContentField(ContentId id);

}
