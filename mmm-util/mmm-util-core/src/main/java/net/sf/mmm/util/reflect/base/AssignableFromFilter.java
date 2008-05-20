/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is a filter that only {@link #accept(Class) accepts} {@link Class types}
 * that are {@link Class#isAssignableFrom(Class) assignable from} a
 * {@link #AssignableFromFilter(Class) special type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AssignableFromFilter implements Filter<Class<?>> {

  /** The class */
  private final Class<?> superClass;

  /**
   * The constructor.
   * 
   * @param superType is the super-type defining which types to
   *        {@link #accept(Class) accept}.
   */
  public AssignableFromFilter(Class<?> superType) {

    super();
    this.superClass = superType;
  }

  /**
   * {@inheritDoc}
   * 
   * @return <code>true</code> if and only if the given <code>type</code> is
   *         {@link Class#isAssignableFrom(Class) assignable from} the
   *         super-type defined at
   *         {@link #AssignableFromFilter(Class) construction}. This means the
   *         given <code>type</code> has to implement/extend the super-type or
   *         be equal to it.
   */
  public boolean accept(Class<?> type) {

    if (type != null) {
      return this.superClass.isAssignableFrom(type);
    }
    return false;
  }

}
