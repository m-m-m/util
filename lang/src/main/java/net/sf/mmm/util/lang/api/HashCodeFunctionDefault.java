/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;

/**
 * This is the default implementation of {@link HashCodeFunction}. It just delegates to {@link Object#hashCode()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public final class HashCodeFunctionDefault extends AbstractHashCodeFunction<Object> implements Serializable {

  private static final long serialVersionUID = 142095355155291498L;

  private static final HashCodeFunctionDefault INSTANCE = new HashCodeFunctionDefault();

  /**
   * The constructor.
   */
  public HashCodeFunctionDefault() {

    super();
  }

  /**
   * @param <V> is the generic type of the result. Typically (and technically) {@link Object}.
   * @return the singleton instance of this {@link HashCodeFunctionDefault}.
   */
  @SuppressWarnings("unchecked")
  public static <V> HashCodeFunction<V> getInstance() {

    return (HashCodeFunction<V>) INSTANCE;
  }

  @Override
  protected int hashCodeNotNull(Object value) {

    return value.hashCode();
  }

}
