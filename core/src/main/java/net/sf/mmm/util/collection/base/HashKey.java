/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import net.sf.mmm.util.lang.api.EqualsChecker;
import net.sf.mmm.util.lang.api.EqualsCheckerIsSame;
import net.sf.mmm.util.lang.api.HashCodeFunction;
import net.sf.mmm.util.lang.api.HashCodeFunctionSystemIdentity;

/**
 * This is a simple class that allows to use any {@link #getDelegate() object} as
 * {@link java.util.Map#get(Object) hash-key}. It only {@link #equals(Object) matches} the exact same instance
 * regardless of the {@link Object#equals(Object)} method of the {@link #getDelegate() delegate-object}.
 * Additionally it sill matches even if the {@link #getDelegate() delegate-object} has been modified and
 * therefore changed its {@link Object#hashCode() hash-code}.
 * 
 * @param <T> is the templated type of the {@link #getDelegate() delegate-object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public final class HashKey<T> extends AbstractHashKey<T> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2492195114074688424L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected HashKey() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param object is the {@link #getDelegate() delegate object}.
   */
  public HashKey(T object) {

    super(object);
  }

  @Override
  protected HashCodeFunction<T> getHashCodeFunction() {

    return HashCodeFunctionSystemIdentity.getInstance();
  }

  @Override
  protected EqualsChecker<T> getEqualsChecker() {

    return EqualsCheckerIsSame.getInstance();
  }

}
