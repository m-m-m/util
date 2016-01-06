/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Date;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsMessage;

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

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessage validateNotNull(Date value) {

    if (value.after(new Date())) {
      return null;
    }
    return createBundle(NlsBundleUtilCoreRoot.class).errorValueNotInFuture(value);
  }

}
