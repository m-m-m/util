/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the abstract base implementation of a simple {@link Datatype}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public abstract class AbstractDatatype<V> implements Datatype<V> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2351955533173401204L;

  /**
   * The constructor.
   */
  public AbstractDatatype() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    V value = getValue();
    if (value == this) {
      return false;
    }
    V otherValue = ((AbstractDatatype<V>) obj).getValue();
    if (value == null) {
      return (otherValue == null);
    } else {
      return value.equals(otherValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    V value = getValue();
    if (value == this) {
      return super.hashCode();
    }
    if (value == null) {
      return 0;
    } else {
      return value.hashCode();
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    V value = getValue();
    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    return getTitle();
  }

}
