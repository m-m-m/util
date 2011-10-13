/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api.access;

import java.util.List;

import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;

/**
 * This interface gives read access to the content-model (reflection).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentReflectionReadAccess extends ContentClassReadAccessById,
    ContentClassReadAccessByTitle, ContentClassReadAccessByInstance, ContentFieldReadAccessById,
    ContentFieldReadAccessByName {

  /**
   * This method gets the root content class that reflects the
   * {@link net.sf.mmm.data.api.ContentObject content-object}.
   * 
   * @return the root class.
   */
  ContentClass<? extends ContentObject> getRootContentClass();

  /**
   * This method gets the list of all registered content classes.
   * 
   * @return the immutable list of all content classes.
   */
  List<? extends ContentClass<? extends ContentObject>> getContentClasses();

}
