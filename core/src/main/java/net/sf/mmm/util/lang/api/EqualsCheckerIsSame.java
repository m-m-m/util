/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is an implementation of {@link EqualsChecker} that simply checks if two objects are same (identical
 * instances, checked via {@code ==} operator).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public final class EqualsCheckerIsSame extends AbstractEqualsChecker<Object> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getInstance() */
  private static final EqualsCheckerIsSame INSTANCE = new EqualsCheckerIsSame();

  /**
   * The constructor. <br>
   * If possible use {@link #getInstance()} instead.
   */
  public EqualsCheckerIsSame() {

    super();
  }

  /**
   * @param <VALUE> is the generic type of the values to {@link #isEqual(Object, Object) check}.
   * @return the singleton instance of this class.
   */
  @SuppressWarnings("unchecked")
  public static <VALUE> EqualsChecker<VALUE> getInstance() {

    return (EqualsChecker<VALUE>) INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isEqualNotNull(Object value1, Object value2) {

    return false;
  }

}
