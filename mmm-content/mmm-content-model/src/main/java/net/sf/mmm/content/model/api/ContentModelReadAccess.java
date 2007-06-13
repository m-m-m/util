/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import java.util.Collection;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.Id;

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
   * @return the content class for the given name.
   * @throws ContentModelException if the requested class does not exist.
   */
  ContentClass getClass(String name) throws ContentModelException;

  /**
   * This method gets the content class for the given ID.
   * 
   * @param id is the ID of the requested class.
   * @return the content class for the given ID.
   * @throws ContentModelException if the requested class does not exist.
   */
  ContentClass getClass(Id id) throws ContentModelException;

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
   * @return an enumeration of all content classes.
   */
  Collection<? extends ContentClass> getClasses();

}
