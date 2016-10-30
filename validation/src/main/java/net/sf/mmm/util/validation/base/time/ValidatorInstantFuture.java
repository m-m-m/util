/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Implementation of {@link ValidatorTimeFuture} for {@link LocalDate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.4.0
 */
public class ValidatorInstantFuture extends ValidatorTimeFuture<Instant> {

  @Override
  protected boolean isFuture(Instant value) {

    return value.isAfter(Instant.now());
  }

}
