/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.Instant;

/**
 * Implementation of {@link ValidatorTimePast} for {@link Instant}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.0.0
 */
public class ValidatorInstantPast extends ValidatorTimePast<Instant> {

  @Override
  protected boolean isPast(Instant value) {

    return value.isBefore(Instant.now());
  }

}
