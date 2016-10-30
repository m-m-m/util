/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.math.BigDecimal;

import javax.inject.Named;
import javax.validation.constraints.DecimalMin;

import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorRange} based on constraint {@link DecimalMin}.
 *
 * @author hohwille
 * @since 7.4.0
 */
@Named
public class ConstraintProcessorDecimalMin implements TypedConstraintProcessor<DecimalMin> {

  @Override
  public Class<DecimalMin> getType() {

    return DecimalMin.class;
  }

  @Override
  public void process(DecimalMin constraint, ConstraintContext context) {

    context.setMinimum(new BigDecimal(constraint.value()));
  }

}
