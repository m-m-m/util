/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import java.util.Map;

import net.sf.mmm.util.pojo.path.api.PojoPathContext;

/**
 * This is the abstract base implementation for a
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathFunction} that operates on a
 * {@link Map}.
 * 
 * @param <VALUE> is the value this function traverses to starting from the
 *        actual POJO.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractMapPojoPathFunction<VALUE> extends
    AbstractPojoPathFunction<Map<String, Object>, VALUE> {

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE get(Map<String, Object> current, String functionName, PojoPathContext context) {

    return (VALUE) current.get(functionName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE set(Map<String, Object> current, String functionName, VALUE value,
      PojoPathContext context) {

    return (VALUE) current.put(functionName, value);
  }

}
