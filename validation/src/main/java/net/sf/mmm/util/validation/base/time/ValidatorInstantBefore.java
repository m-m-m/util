/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.Instant;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * Implementation of {@link ValidatorTimeBefore} for {@link Instant}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.5.0
 */
public class ValidatorInstantBefore extends ValidatorTimeBefore<Instant> {

  /**
   * The constructor.
   *
   * @param valueSource the {@link AttributeReadValue source} of the value to compare to.
   */
  public ValidatorInstantBefore(AttributeReadValue<Instant> valueSource) {
    super(valueSource);
  }

  /**
   * The constructor.
   *
   * @param value the value to compare to.
   */
  public ValidatorInstantBefore(Instant value) {
    super(value);
  }

  @Override
  protected boolean isBefore(Instant value, Instant limit) {

    return value.isBefore(limit);
  }

}
