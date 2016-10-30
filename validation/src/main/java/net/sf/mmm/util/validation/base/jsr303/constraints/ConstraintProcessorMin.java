/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.inject.Named;
import javax.validation.constraints.Min;

import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorRange} based on constraint {@link Min}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class ConstraintProcessorMin implements TypedConstraintProcessor<Min> {

  @Override
  public Class<Min> getType() {

    return Min.class;
  }

  @Override
  public void process(Min constraint, ConstraintContext context) {

    context.setMinimum(Long.valueOf(constraint.value()));
  }

}
