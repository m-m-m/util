/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api.access;

import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;

/**
 * This interface allows to {@link #getContentClass(String) get} a
 * {@link ContentClass} by its {@link ContentClass#getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentClassReadAccessByTitle {

  /**
   * This method gets the content class for the given name.
   * 
   * @param title is the {@link ContentClass#getTitle() title} of the requested
   *        class.
   * @return the {@link ContentClass} for the given name or <code>null</code> if
   *         no such class exist.
   */
  ContentClass<? extends ContentObject> getContentClass(String title);

}
