/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.EqualsChecker;
import net.sf.mmm.util.lang.api.HashCodeFunction;
import net.sf.mmm.util.lang.api.HashCodeFunctionDefault;

/**
 * This is a simple wrapper in order to use an object as hash key but with ability to customize
 * {@link Object#equals(Object) equals} and {@link #hashCode() hashCode}.
 *
 * @param <T> is the generic type of the {@link #getDelegate() delegate-object}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class CustomHashKey<T> extends AbstractHashKey<T> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1601614479939341307L;

  /** @see #equals(Object) */
  private EqualsChecker<T> equalsChecker;

  /** @see #hashCode() */
  private HashCodeFunction<T> hashCodeFunction;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected CustomHashKey() {

    super();
  }

  /**
   * The constructor.
   *
   * @param object is the {@link #getDelegate() delegate object}.
   * @param equalsChecker is the {@link EqualsChecker} used to customize {@link #equals(Object)}.
   */
  @SuppressWarnings("unchecked")
  public CustomHashKey(T object, EqualsChecker<T> equalsChecker) {

    this(object, equalsChecker, (HashCodeFunction<T>) HashCodeFunctionDefault.getInstance());
  }

  /**
   * The constructor.
   *
   * @param object is the {@link #getDelegate() delegate object}.
   * @param equalsChecker is the {@link EqualsChecker} used to customize {@link #equals(Object)}.
   * @param hashCodeFunction is the {@link HashCodeFunction} used to customize {@link #hashCode()}.
   */
  public CustomHashKey(T object, EqualsChecker<T> equalsChecker, HashCodeFunction<T> hashCodeFunction) {

    super(object);
    NlsNullPointerException.checkNotNull(EqualsChecker.class, equalsChecker);
    NlsNullPointerException.checkNotNull(HashCodeFunction.class, hashCodeFunction);
    this.equalsChecker = equalsChecker;
    this.hashCodeFunction = hashCodeFunction;
  }

  @Override
  protected EqualsChecker<T> getEqualsChecker() {

    return this.equalsChecker;
  }

  @Override
  protected HashCodeFunction<T> getHashCodeFunction() {

    return this.hashCodeFunction;
  }

}
