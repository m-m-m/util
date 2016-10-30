/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.validation.base.ValidatorMandatory;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorMandatory}. Should be registered for constraints
 * such as {@link javax.validation.constraints.NotNull}, {@link org.hibernate.validator.constraints.NotEmpty} or
 * {@link net.sf.mmm.util.validation.base.Mandatory}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class ConstraintProcessorMandatory implements ConstraintProcessor<Annotation> {

  @Override
  public void process(Annotation constraint, ConstraintContext context) {

    context.getValidatorRegistry().add(ValidatorMandatory.getInstance());
  }

}
