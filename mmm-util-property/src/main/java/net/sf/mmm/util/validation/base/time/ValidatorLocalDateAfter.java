/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDate;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * Implementation of {@link ValidatorTimeAfter} for {@link LocalDate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.0.0
 */
public class ValidatorLocalDateAfter extends ValidatorTimeAfter<LocalDate> {

  /**
   * The constructor.
   *
   * @param valueSource the {@link AttributeReadValue source} of the value to compare to.
   */
  public ValidatorLocalDateAfter(AttributeReadValue<LocalDate> valueSource) {
    super(valueSource);
  }

  /**
   * The constructor.
   *
   * @param value the value to compare to.
   */
  public ValidatorLocalDateAfter(LocalDate value) {
    super(value);
  }

  @Override
  protected boolean isAfter(LocalDate value, LocalDate limit) {

    return value.isAfter(limit);
  }

}
