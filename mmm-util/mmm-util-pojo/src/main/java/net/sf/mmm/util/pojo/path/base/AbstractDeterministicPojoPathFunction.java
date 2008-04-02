/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.pojo.path.api.PojoPathFunction;

/**
 * This is the abstract base implementation for
 * {@link #isDeterministic() deterministic} {@link PojoPathFunction}s.
 * 
 * @param <ACTUAL> is the actual POJO this function operates on.
 * @param <VALUE> is the value this function traverses to starting from the
 *        actual POJO.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDeterministicPojoPathFunction<ACTUAL, VALUE> implements
    PojoPathFunction<ACTUAL, VALUE> {

  /**
   * The constructor.
   */
  public AbstractDeterministicPojoPathFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   * 
   * This implementation always returns <code>true</code>. Override for
   * indeterministic implementations.
   */
  public boolean isDeterministic() {

    return true;
  }

}
