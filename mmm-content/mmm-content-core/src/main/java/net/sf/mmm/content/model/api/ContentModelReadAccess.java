/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import java.util.List;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This interface gives read access to the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelReadAccess {

  /**
   * This method gets the content class for the given name.
   * 
   * @param name is the name of the requested class.
   * @return the content class for the given name or <code>null</code> if it
   *         does NOT exist.
   */
  ContentClass getClass(String name);

  /**
   * This method gets the {@link ContentClass} for the given <code>id</code>.
   * 
   * @param id is the unique ID of the requested class.
   * @return the content class for the given ID or <code>null</code> if it
   *         does NOT exist.
   */
  ContentClass getClass(ContentId id);

  /**
   * This method gets the {@link ContentField} for the given <code>id</code>.
   * 
   * @param id is the unique ID of the requested class.
   * @return the content field for the given ID or <code>null</code> if it
   *         does NOT exist.
   */
  ContentField getField(ContentId id);

  /**
   * This method gets the root content class that reflects the
   * {@link ContentObject content-object}.
   * 
   * @return the root class.
   */
  ContentClass getRootClass();

  /**
   * This method gets the list of all registered content classes.
   * 
   * @return the immutable list of all content classes.
   */
  List<? extends ContentClass> getClasses();

}
