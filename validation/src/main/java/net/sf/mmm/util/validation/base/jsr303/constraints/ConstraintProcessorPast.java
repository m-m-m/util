/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.inject.Named;
import javax.validation.constraints.Past;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.ValidatorDatePast;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;
import net.sf.mmm.util.validation.base.time.ValidatorInstantPast;
import net.sf.mmm.util.validation.base.time.ValidatorLocalDatePast;
import net.sf.mmm.util.validation.base.time.ValidatorLocalDateTimePast;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorDatePast} based on constraint {@link Past}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
public class ConstraintProcessorPast implements TypedConstraintProcessor<Past> {

  @Override
  public Class<Past> getType() {

    return Past.class;
  }

  @Override
  public void process(Past constraint, ConstraintContext context) {

    GenericType<?> valueType = context.getValueType();
    Class<?> valueClass = valueType.getAssignmentClass();
    if (valueClass == Date.class) {
      context.getValidatorRegistry().add(new ValidatorDatePast());
    } else if (valueClass == Instant.class) {
      context.getValidatorRegistry().add(new ValidatorInstantPast());
    } else if (valueClass == LocalDate.class) {
      context.getValidatorRegistry().add(new ValidatorLocalDatePast());
    } else if (valueClass == LocalDateTime.class) {
      context.getValidatorRegistry().add(new ValidatorLocalDateTimePast());
    } else {
      throw new IllegalCaseException(valueClass.getName());
    }
  }

}
