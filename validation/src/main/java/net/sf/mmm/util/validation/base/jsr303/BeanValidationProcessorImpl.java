/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import javax.validation.constraints.NotNull;

import net.sf.mmm.util.validation.base.Mandatory;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessor;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorDecimalMax;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorDecimalMin;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorFuture;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMandatory;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMax;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMin;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorPast;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorPattern;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorSize;

/**
 * The (default) implementation of {@link BeanValidationProcessor}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanValidationProcessorImpl extends AbstractBeanValidationProcessor {

  /**
   * The constructor.
   */
  public BeanValidationProcessorImpl() {
    super();
    registerProcessors();
  }

  /**
   * Registers the {@link ConstraintProcessor}s.
   */
  private void registerProcessors() {

    ConstraintProcessorMandatory mandatory = new ConstraintProcessorMandatory();
    registerProcessor("org.hibernate.validator.constraints.NotEmpty", mandatory);
    registerProcessor(NotNull.class, mandatory);
    registerProcessor(Mandatory.class, mandatory);
    registerProcessor(new ConstraintProcessorSize());
    registerProcessor(new ConstraintProcessorMin());
    registerProcessor(new ConstraintProcessorMax());
    registerProcessor(new ConstraintProcessorDecimalMax());
    registerProcessor(new ConstraintProcessorDecimalMin());
    registerProcessor(new ConstraintProcessorFuture());
    registerProcessor(new ConstraintProcessorPast());
    registerProcessor(new ConstraintProcessorPattern());
  }

}
