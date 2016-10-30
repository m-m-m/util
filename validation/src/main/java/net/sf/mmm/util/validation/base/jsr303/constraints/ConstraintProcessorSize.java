/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.inject.Named;
import javax.validation.constraints.Size;

import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorRange} based on constraint {@link Size}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class ConstraintProcessorSize implements TypedConstraintProcessor<Size> {

  @Override
  public Class<Size> getType() {

    return Size.class;
  }

  @Override
  public void process(Size constraint, ConstraintContext context) {

    context.setMinimum(Integer.valueOf(constraint.min()));
    context.setMaximum(Integer.valueOf(constraint.max()));
  }

}
