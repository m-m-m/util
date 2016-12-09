/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDateTime;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * Implementation of {@link ValidatorTimeAfter} for {@link LocalDateTime}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.4.0
 */
public class ValidatorLocalDateTimeAfter extends ValidatorTimeAfter<LocalDateTime> {

  /**
   * The constructor.
   *
   * @param valueSource the {@link AttributeReadValue source} of the value to compare to.
   */
  public ValidatorLocalDateTimeAfter(AttributeReadValue<LocalDateTime> valueSource) {
    super(valueSource);
  }

  /**
   * The constructor.
   *
   * @param value the value to compare to.
   */
  public ValidatorLocalDateTimeAfter(LocalDateTime value) {
    super(value);
  }

  @Override
  protected boolean isAfter(LocalDateTime value, LocalDateTime limit) {

    return value.isAfter(limit);
  }

}