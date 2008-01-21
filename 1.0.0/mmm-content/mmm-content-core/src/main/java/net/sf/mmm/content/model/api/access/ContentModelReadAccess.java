/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api.access;

import java.util.List;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;

/**
 * This interface gives read access to the content-model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelReadAccess extends ContentClassReadAccessById,
    ContentClassReadAccessByName, ContentFieldReadAccessById {

  /**
   * This method gets the root content class that reflects the
   * {@link ContentObject content-object}.
   * 
   * @return the root class.
   */
  ContentClass getRootContentClass();

  /**
   * This method gets the list of all registered content classes.
   * 
   * @return the immutable list of all content classes.
   */
  List<? extends ContentClass> getContentClasses();

}
