/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDateTime;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * Implementation of {@link ValidatorTimeBefore} for {@link LocalDateTime}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.0.0
 */
public class ValidatorLocalDateTimeBefore extends ValidatorTimeBefore<LocalDateTime> {

  /**
   * The constructor.
   *
   * @param valueSource the {@link AttributeReadValue source} of the value to compare to.
   */
  public ValidatorLocalDateTimeBefore(AttributeReadValue<LocalDateTime> valueSource) {
    super(valueSource);
  }

  /**
   * The constructor.
   *
   * @param value the value to compare to.
   */
  public ValidatorLocalDateTimeBefore(LocalDateTime value) {
    super(value);
  }

  @Override
  protected boolean isBefore(LocalDateTime value, LocalDateTime limit) {

    return value.isBefore(limit);
  }

}
