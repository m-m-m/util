/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is an implementation of the {@link WildcardType} interface for a single
 * upper bound.
 * 
 * @see ReflectionUtilImpl#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class UpperBoundWildcardType implements WildcardType {

  /** @see #getUpperBounds() */
  private final Type upperBound;

  /**
   * The constructor.
   * 
   * @param upperBound is the single {@link #getUpperBounds() upper-bound}.
   */
  public UpperBoundWildcardType(Type upperBound) {

    super();
    this.upperBound = upperBound;
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getUpperBounds() {

    return new Type[] { this.upperBound };
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getLowerBounds() {

    return ReflectionUtil.NO_TYPES;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if ((other != null) && (other instanceof WildcardType)) {
      WildcardType otherWildcard = (WildcardType) other;
      if ((otherWildcard.getLowerBounds().length == 0)
          && (otherWildcard.getUpperBounds().length == 1)
          && (this.upperBound.equals(otherWildcard.getUpperBounds()[0]))) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    // emulate hash code of
    // sun.reflect.generics.reflectiveObjects.WildcardTypeImpl
    return ~(31 + this.upperBound.hashCode());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String lowerBoundString = ReflectionUtilImpl.getInstance().toString(this.upperBound);
    // "? extends ".length == 10
    StringBuilder result = new StringBuilder(lowerBoundString.length() + 10);
    result.append("? extends ");
    result.append(lowerBoundString);
    return result.toString();
  }

}
