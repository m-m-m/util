/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.lang.api.attribute.AttributeReadName;

/**
 * This is the root element of a {@link Path}. It is typically an {@link net.sf.mmm.util.query.api.path.EntityAlias} or
 * a {@link net.sf.mmm.util.query.api.variable.Variable}. A {@link PathRoot} itself is NOT an instance of {@link Path}.
 *
 * @param <E> the generic type of the {@link #getPrototype() prototype}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface PathRoot<E> extends PathFactory, AttributeReadName {

  /**
   * @return the {@link BeanFactory#createPrototype(Class) prototype} of this root. May be {@code null}.
   */
  E getPrototype();

}
