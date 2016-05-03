/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;
import net.sf.mmm.util.validation.base.AbstractValueValidator;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that
 * {@link net.sf.mmm.util.validation.api.ValueValidator#validate(Object, Object) validates} that a date lies in the
 * past.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.0.0
 */
public abstract class ValidatorTimePast<V> extends AbstractValueValidator<V> {

  /** @see #getCode() */
  public static final String CODE = "Past";

  /**
   * The constructor.
   */
  public ValidatorTimePast() {

    super();
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  @Override
  protected NlsMessage validateNotNull(V value) {

    if (isPast(value)) {
      return null;
    }
    return createBundle(NlsBundleUtilValidationRoot.class).errorValueNotInPast(value);
  }

  /**
   * @param value the date to check.
   * @return {@code true} if in future, {@code false} otherwise.
   */
  protected abstract boolean isPast(V value);

  @Override
  public int hashCode() {

    return 2424;
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
    return true;
  }
}
