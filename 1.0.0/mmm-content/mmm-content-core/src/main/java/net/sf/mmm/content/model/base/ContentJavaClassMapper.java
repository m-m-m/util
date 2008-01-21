/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.api.ContentObject;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentJavaClassMapper {

  /**
   * This method gets the
   * {@link net.sf.mmm.content.model.api.ContentClass#getJavaClass() java-class}
   * for the {@link net.sf.mmm.content.model.api.ContentClass content-class}
   * with the given
   * <code>ContentClass{@link net.sf.mmm.content.model.api.ContentClass#getName() contentClassName}</code>.
   * 
   * @param contentClassName is the name of the
   *        {@link net.sf.mmm.content.model.api.ContentClass content-class} for
   *        which the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getJavaClass() java-class}
   *        is requested.
   * @return the requested class or <code>null</code> if the given
   *         <code>contentClassName</code> is NOT registered.
   */
  Class<? extends ContentObject> getJavaClass(String contentClassName);

}
