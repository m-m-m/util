/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the abstract base implementation of {@link HashCodeFunction}. It contains the handling of
 * <code>null</code> values.
 * 
 * @param <VALUE> is the generic type of the objects to {@link #hashCode(Object) hash}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractHashCodeFunction<VALUE> implements HashCodeFunction<VALUE> {

  /**
   * The constructor.
   */
  public AbstractHashCodeFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode(VALUE value) {

    if (value == null) {
      return 0;
    }
    return hashCodeNotNull(value);
  }

  /**
   * @see #hashCode(Object)
   * 
   * @param value is the object to hash. Will NOT be <code>null</code>.
   * @return the custom {@link #hashCode(Object) hash code} of the given <code>value</code>.
   */
  protected abstract int hashCodeNotNull(VALUE value);

}
