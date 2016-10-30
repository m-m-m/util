/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import net.sf.mmm.util.validation.base.Mandatory;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessor;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessorMandatory;
import net.sf.mmm.util.validation.base.jsr303.constraints.TypedConstraintProcessor;

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
   * @param processors the {@link List} of {@link TypedConstraintProcessor} to {@link Inject} and
   *        {@link #registerProcessor(TypedConstraintProcessor) register}.
   */
  @Inject
  public void setTypedConstraintProcessors(List<TypedConstraintProcessor<?>> processors) {

    for (TypedConstraintProcessor<?> typedProcessor : processors) {
      registerProcessor(typedProcessor);
    }
  }

  /**
   * Registers the {@link ConstraintProcessor}s.
   */
  private void registerProcessors() {

    ConstraintProcessorMandatory mandatory = new ConstraintProcessorMandatory();
    registerProcessor("org.hibernate.validator.constraints.NotEmpty", mandatory);
    registerProcessor(NotNull.class, mandatory);
    registerProcessor(Mandatory.class, mandatory);
  }

}
