/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.ConcurrentMap;

import net.sf.mmm.util.collection.api.ConcurrentMapFactory;
import net.sf.mmm.util.collection.base.AbstractMapFactory;

/**
 * This is the abstract base implementation of the {@link ConcurrentMapFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConcurrentMapFactory extends AbstractMapFactory implements ConcurrentMapFactory {

  /**
   * The constructor.
   */
  public AbstractConcurrentMapFactory() {

    super();
  }

}
