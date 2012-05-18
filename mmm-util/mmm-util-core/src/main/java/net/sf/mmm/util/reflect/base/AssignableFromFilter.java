/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is a filter that only {@link #accept(Class) accepts} {@link Class types} that are
 * {@link Class#isAssignableFrom(Class) assignable from} a {@link #AssignableFromFilter(Class) special type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class AssignableFromFilter implements Filter<Class<?>> {

  /** The class */
  private final Class<?> superClass;

  /** @see #accept(Class) */
  private final boolean excludeSuperType;

  /**
   * The constructor.
   * 
   * @param superClass is the super-type defining which types to {@link #accept(Class) accept}.
   */
  public AssignableFromFilter(Class<?> superClass) {

    this(superClass, false);
  }

  /**
   * The constructor.
   * 
   * @param superClass is the super-type defining which types to {@link #accept(Class) accept}.
   * @param excludeSuperClass - <code>true</code> if the given <code>superClass</code> itself shall NOT be
   *        {@link #accept(Class) accepted}, <code>false</code> otherwise.
   * @since 2.0.2
   */
  public AssignableFromFilter(Class<?> superClass, boolean excludeSuperClass) {

    super();
    this.superClass = superClass;
    this.excludeSuperType = excludeSuperClass;
  }

  /**
   * {@inheritDoc}
   * 
   * @return <code>true</code> if and only if the given <code>type</code> is
   *         {@link Class#isAssignableFrom(Class) assignable from} the super-type defined at
   *         {@link #AssignableFromFilter(Class) construction}. This means the given <code>type</code> has to
   *         implement/extend the super-type or be equal to it.
   */
  public boolean accept(Class<?> type) {

    if (type != null) {
      if (this.excludeSuperType) {
        if (this.superClass.equals(type)) {
          return false;
        }
      }
      return this.superClass.isAssignableFrom(type);
    }
    return false;
  }

}
