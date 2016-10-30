/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.query.api.path.PathRoot;

/**
 * This is the abstract base implementation of {@link PathRoot}.
 *
 * @param <E> the generic type of the {@link #getPrototype() prototype}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractPathRoot<E> extends AbstractPathFactory implements PathRoot<E> {

  /**
   * The constructor.
   */
  public AbstractPathRoot() {
    super();
  }

}
