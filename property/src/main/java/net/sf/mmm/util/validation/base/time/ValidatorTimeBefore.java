/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.util.Objects;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;
import net.sf.mmm.util.validation.base.AbstractValueValidator;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that
 * {@link net.sf.mmm.util.validation.api.ValueValidator#validate(Object, Object) validates} that a date/time lies before
 * a given value.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.0.0
 */
public abstract class ValidatorTimeBefore<V> extends AbstractValueValidator<V> {

  /** @see #getCode() */
  public static final String CODE = "Before";

  private final AttributeReadValue<V> boundSource;

  private final V bound;

  /**
   * The constructor.
   *
   * @param valueSource the {@link AttributeReadValue source} of the value to compare to.
   */
  public ValidatorTimeBefore(AttributeReadValue<V> valueSource) {

    super();
    this.boundSource = valueSource;
    this.bound = null;
  }

  /**
   * The constructor.
   *
   * @param value the value to compare to.
   */
  public ValidatorTimeBefore(V value) {

    super();
    this.boundSource = null;
    this.bound = value;
  }

  @Override
  public boolean isDynamic() {

    return (this.boundSource != null);
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessage validateNotNull(V value) {

    V limit;
    if (this.boundSource == null) {
      limit = this.bound;
    } else {
      limit = this.boundSource.getValue();
    }
    if (isBefore(value, limit)) {
      return null;
    }
    return createBundle(NlsBundleUtilValidationRoot.class).errorValueNotBefore(value, limit);
  }

  /**
   * @param value the date to check.
   * @param limit the upper bound to compare to.
   * @return {@code true} if {@code value} is before {@code limit}, {@code false} otherwise.
   */
  protected abstract boolean isBefore(V value, V limit);

  @Override
  public int hashCode() {

    if (this.boundSource != null) {
      return ~this.boundSource.hashCode();
    }
    return ~Objects.hashCode(this.bound);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ValidatorTimeBefore<?> other = (ValidatorTimeBefore<?>) obj;
    if (!Objects.equals(this.bound, other.bound)) {
      return false;
    }
    if (!Objects.equals(this.boundSource, other.boundSource)) {
      return false;
    }
    return true;
  }
}
