/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;

/**
 * This is the abstract base implementation of {@link EqualsChecker}. It contains the handling of
 * {@code null} values.
 *
 * @param <VALUE> is the generic type of the values to {@link #isEqual(Object, Object) check}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEqualsChecker<VALUE> implements EqualsChecker<VALUE>, Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractEqualsChecker() {

    super();
  }

  @Override
  public final boolean isEqual(VALUE value1, VALUE value2) {

    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    }
    return isEqualNotNull(value1, value2);
  }

  /**
   * Called from {@link #isEqual(Object, Object)} if objects are both NOT same and NOT null.
   *
   * @param value1 is the first value to check.
   * @param value2 is the first value to check.
   * @return {@code true} if the given values are considered as equal, {@code false} otherwise.
   */
  protected abstract boolean isEqualNotNull(VALUE value1, VALUE value2);

}
