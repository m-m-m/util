/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.inject.Named;
import javax.validation.constraints.Max;

import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorRange} based on constraint {@link Max}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class ConstraintProcessorMax implements TypedConstraintProcessor<Max> {

  @Override
  public Class<Max> getType() {

    return Max.class;
  }

  @Override
  public void process(Max constraint, ConstraintContext context) {

    context.setMaximum(Long.valueOf(constraint.value()));
  }

}
