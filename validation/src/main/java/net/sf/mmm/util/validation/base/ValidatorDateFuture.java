/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Date;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that
 * {@link net.sf.mmm.util.validation.api.ValueValidator#validate(Object, Object) validates that a date} lies in the
 * future.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorDateFuture extends AbstractValueValidator<Date> {

  /** @see #getCode() */
  public static final String CODE = "Future";

  /**
   * The constructor.
   */
  public ValidatorDateFuture() {

    super();
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  @Override
  protected NlsMessage validateNotNull(Date value) {

    if (value.after(new Date())) {
      return null;
    }
    return createBundle(NlsBundleUtilValidationRoot.class).errorValueNotInFuture(value);
  }

  @Override
  public int hashCode() {

    return CODE.hashCode();
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
