/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api.access;

import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getContentClass(Object) get} a
 * {@link ContentClass} for a given {@link net.sf.mmm.content.api.ContentObject
 * instance}.
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentClassReadAccessByInstance<CLASS> {

  /**
   * This method gets the {@link ContentClass} for the given
   * <code>contentObject</code>.
   * 
   * @param <C> is the generic type of the given <code>contentObject</code>.
   * 
   * @see ContentClass#getJavaClass()
   * 
   * @param contentObject is the instance for which the {@link ContentClass} is
   *        requested.
   * @return the {@link ContentClass} for the given <code>contentObject</code>.
   * @throws ObjectNotFoundException if the given <code>contentObject</code> is
   *         unknown by the content-model.
   */
  <C extends CLASS> ContentClass<C> getContentClass(C contentObject) throws ObjectNotFoundException;

}
