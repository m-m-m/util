/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.math.BigDecimal;

import javax.inject.Named;
import javax.validation.constraints.DecimalMax;

import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorRange} based on constraint {@link DecimalMax}.
 *
 * @author hohwille
 * @since 7.4.0
 */
@Named
public class ConstraintProcessorDecimalMax implements TypedConstraintProcessor<DecimalMax> {

  @Override
  public Class<DecimalMax> getType() {

    return DecimalMax.class;
  }

  @Override
  public void process(DecimalMax constraint, ConstraintContext context) {

    context.setMaximum(new BigDecimal(constraint.value()));
  }

}
