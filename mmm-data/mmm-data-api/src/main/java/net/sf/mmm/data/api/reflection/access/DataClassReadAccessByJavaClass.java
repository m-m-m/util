/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This interface allows to {@link #getDataClass(Class) get} a {@link DataClass}
 * for a given {@link DataClass#getJavaClass() java class}.<br/>
 * <b>ATTENTION:</b><br/>
 * This API only makes sense for statically typed {@link DataClass} models.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassReadAccessByJavaClass {

  /**
   * This method gets the {@link DataClass} for the given
   * <code>{@link Class javaClass}</code>.
   * 
   * @param <CLASS> is the generic type of the given <code>javaClass</code>.
   * 
   * @see DataClass#getJavaClass()
   * 
   * @param javaClass is the {@link DataClass#getJavaClass() java class} for
   *        which the {@link DataClass} is requested.
   * @return the {@link DataClass} for the given <code>javaClass</code>. In case
   *         of a dynamically typed {@link DataClass} this method will return
   *         the most general {@link DataClass} for the given
   *         <code>javaClass</code>.
   * @throws ObjectNotFoundException if the given <code>javaClass</code> is
   *         unknown.
   */
  <CLASS extends DataObjectView> DataClass<CLASS> getDataClass(Class<CLASS> javaClass)
      throws ObjectNotFoundException;

}
