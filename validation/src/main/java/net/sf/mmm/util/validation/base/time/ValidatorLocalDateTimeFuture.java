/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDateTime;

/**
 * Implementation of {@link ValidatorTimeFuture} for {@link LocalDateTime}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.4.0
 */
public class ValidatorLocalDateTimeFuture extends ValidatorTimeFuture<LocalDateTime> {

  @Override
  protected boolean isFuture(LocalDateTime value) {

    return value.isAfter(LocalDateTime.now());
  }

}
