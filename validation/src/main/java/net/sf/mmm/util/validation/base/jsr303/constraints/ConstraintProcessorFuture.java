/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.inject.Named;
import javax.validation.constraints.Future;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.ValidatorDateFuture;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;
import net.sf.mmm.util.validation.base.time.ValidatorInstantFuture;
import net.sf.mmm.util.validation.base.time.ValidatorLocalDateFuture;
import net.sf.mmm.util.validation.base.time.ValidatorLocalDateTimeFuture;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorDateFuture} based on constraint {@link Future}.
 *
 * @author hohwille
 * @since 8.5.0
 */
@Named
public class ConstraintProcessorFuture implements TypedConstraintProcessor<Future> {

  @Override
  public Class<Future> getType() {

    return Future.class;
  }

  @Override
  public void process(Future constraint, ConstraintContext context) {

    GenericType<?> valueType = context.getValueType();
    Class<?> valueClass = valueType.getAssignmentClass();
    if (valueClass == Date.class) {
      context.getValidatorRegistry().add(new ValidatorDateFuture());
    } else if (valueClass == Instant.class) {
      context.getValidatorRegistry().add(new ValidatorInstantFuture());
    } else if (valueClass == LocalDate.class) {
      context.getValidatorRegistry().add(new ValidatorLocalDateFuture());
    } else if (valueClass == LocalDateTime.class) {
      context.getValidatorRegistry().add(new ValidatorLocalDateTimeFuture());
    } else {
      throw new IllegalCaseException(valueClass.getName());
    }
  }

}
