/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;

/**
 * This is an implementation of {@link HashCodeFunction} that delegates to
 * {@link System#identityHashCode(Object)} and conforms to {@link EqualsCheckerIsSame}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public final class HashCodeFunctionSystemIdentity extends AbstractHashCodeFunction<Object> implements Serializable {

  private static final long serialVersionUID = 142095355155291498L;

  private  static final HashCodeFunctionSystemIdentity INSTANCE = new HashCodeFunctionSystemIdentity();

  /**
   * The constructor.
   */
  public HashCodeFunctionSystemIdentity() {

    super();
  }

  /**
   * @param <V> is the generic type of the result. Typically (and technically) {@link Object}.
   * @return the singleton instance of this {@link HashCodeFunctionSystemIdentity}.
   */
  @SuppressWarnings("unchecked")
  public static <V> HashCodeFunction<V> getInstance() {

    return (HashCodeFunction<V>) INSTANCE;
  }

  @Override
  protected int hashCodeNotNull(Object value) {

    return System.identityHashCode(value);
  }

}
