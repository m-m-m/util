/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataClass(DataClass) get} a
 * {@link DataClass} for a given {@link net.sf.mmm.data.api.DataObjectView instance}
 * .
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassReadAccessByInstance {

  /**
   * This method gets the {@link DataClass} for the given
   * <code>dataObject</code>.
   * 
   * @param <O> is the generic type of the given <code>dataObject</code>.
   * 
   * @see DataClass#getJavaClass()
   * 
   * @param dataObject is the instance for which the {@link DataClass} is
   *        requested.
   * @return the {@link DataClass} for the given <code>dataObject</code>.
   * @throws ObjectNotFoundException if the given <code>dataObject</code> is
   *         unknown.
   */
  <O extends DataObjectView> DataClass<O> getDataClass(O dataObject) throws ObjectNotFoundException;

}
