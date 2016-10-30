/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.validation.constraints.Future;

import net.sf.mmm.util.validation.base.ValidatorDateFuture;
import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorDateFuture} based on constraint {@link Future}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class ConstraintProcessorFuture implements TypedConstraintProcessor<Future> {

  @Override
  public Class<Future> getType() {

    return Future.class;
  }

  @Override
  public void process(Future constraint, ConstraintContext context) {

    context.getValidatorRegistry().add(new ValidatorDateFuture());
  }

}
