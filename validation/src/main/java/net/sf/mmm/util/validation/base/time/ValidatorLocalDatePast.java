/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDate;

/**
 * Implementation of {@link ValidatorTimePast} for {@link LocalDate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.5.0
 */
public class ValidatorLocalDatePast extends ValidatorTimePast<LocalDate> {

  @Override
  protected boolean isPast(LocalDate value) {

    return value.isBefore(LocalDate.now());
  }

}
