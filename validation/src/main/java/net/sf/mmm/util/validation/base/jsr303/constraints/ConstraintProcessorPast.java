/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.validation.constraints.Past;

import net.sf.mmm.util.validation.base.ValidatorDatePast;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorDatePast} based on constraint {@link Past}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstraintProcessorPast implements TypedConstraintProcessor<Past> {

  @Override
  public Class<Past> getType() {

    return Past.class;
  }

  @Override
  public void process(Past constraint, ConstraintContext context) {

    context.getValidatorRegistry().add(new ValidatorDatePast());
  }

}
