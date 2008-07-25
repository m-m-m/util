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
 * lower bound.
 * 
 * @see ReflectionUtilImpl#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LowerBoundWildcardType implements WildcardType {

  /** @see #getLowerBounds() */
  private final Type lowerBound;

  /**
   * The constructor.
   * 
   * @param lowerBound is the single {@link #getLowerBounds() lower-bound}.
   */
  public LowerBoundWildcardType(Type lowerBound) {

    super();
    this.lowerBound = lowerBound;
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getLowerBounds() {

    return new Type[] { this.lowerBound };
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getUpperBounds() {

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
      if ((otherWildcard.getLowerBounds().length == 1)
          && (otherWildcard.getUpperBounds().length == 0)
          && (this.lowerBound.equals(otherWildcard.getLowerBounds()[0]))) {
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
    return ~(31 + this.lowerBound.hashCode());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String upperBoundString = ReflectionUtilImpl.getInstance().toString(this.lowerBound);
    // "? super ".length == 8
    StringBuilder result = new StringBuilder(upperBoundString.length() + 8);
    result.append("? super ");
    result.append(upperBoundString);
    return result.toString();
  }

}
