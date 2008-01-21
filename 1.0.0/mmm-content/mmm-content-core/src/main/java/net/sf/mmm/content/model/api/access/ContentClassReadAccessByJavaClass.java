/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api.access;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This interface allows to {@link #getContentClass(Class) get} a
 * {@link ContentClass} by its {@link ContentId ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassReadAccessByJavaClass {

  /**
   * This method gets the {@link ContentClass} for the given
   * <code>javaClass</code>.
   * 
   * @see ContentClass#getJavaClass()
   * 
   * @param javaClass is the native
   *        {@link ContentClass#getJavaClass() javaClass} for which the
   *        {@link ContentClass} is requested.
   * @return the {@link ContentClass} for the given <code>javaClass</code> or
   *         <code>null</code> if the given <code>javaClass</code> is NO
   *         {@link ContentObject entity-type}.
   */
  ContentClass getContentClass(Class<? extends ContentObject> javaClass);

}
