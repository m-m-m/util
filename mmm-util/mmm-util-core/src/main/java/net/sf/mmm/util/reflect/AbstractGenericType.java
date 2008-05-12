/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Type;

/**
 * This is the implementation of the {@link GenericType} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericType implements GenericType {

  /**
   * The constructor.
   */
  protected AbstractGenericType() {

    super();
  }

  /**
   * This method gets the defining type.
   * 
   * @return the defining type or <code>null</code> if NOT available.
   */
  public abstract GenericType getDefiningType();

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object other) {

    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (other instanceof AbstractGenericType) {
      AbstractGenericType otherType = (AbstractGenericType) other;
      if (getType().equals(otherType.getType())) {
        GenericType definingType = getDefiningType();
        if (definingType == null) {
          return (otherType.getDefiningType() == null);
        } else {
          return (definingType.equals(otherType.getDefiningType()));
        }
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    int hash = getType().hashCode();
    GenericType definingType = getDefiningType();
    if (definingType != null) {
      hash = hash * 31 + definingType.hashCode();
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    Type type = getType();
    Class<?> upperBound = getUpperBound();
    if (upperBound == type) {
      return upperBound.getName();
    } else {
      return type.toString();
    }
  }

}
