/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorDecimalMax;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorDecimalMin;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorFutureDate;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMax;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMin;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorPastDate;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorPattern;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorSize;

/**
 * This is the default implementation of {@link BeanValidationProcessor} to be used in case no dependency injection is
 * available.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class DefaultBeanValidationProcessor extends BeanValidationProcessorImpl {

  @Override
  protected void doInitialize() {

    super.doInitialize();
    registerProcessor(new ConstraintProcessorSize());
    registerProcessor(new ConstraintProcessorMin());
    registerProcessor(new ConstraintProcessorMax());
    registerProcessor(new ConstraintProcessorDecimalMax());
    registerProcessor(new ConstraintProcessorDecimalMin());
    registerProcessor(new ConstraintProcessorPattern());
    registerProcessor(new ConstraintProcessorFutureDate());
    registerProcessor(new ConstraintProcessorPastDate());
  }

}
