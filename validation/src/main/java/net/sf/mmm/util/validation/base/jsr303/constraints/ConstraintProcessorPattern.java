/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import javax.inject.Named;
import javax.validation.constraints.Pattern;

import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;
import net.sf.mmm.util.validation.base.text.ValidatorPattern;

/**
 * Implementation of {@link ConstraintProcessor} for {@link ValidatorPattern} based on constraint {@link Pattern}.
 *
 * @author hohwille
 * @since 7.4.0
 */
@Named
public class ConstraintProcessorPattern implements TypedConstraintProcessor<Pattern> {

  @Override
  public Class<Pattern> getType() {

    return Pattern.class;
  }

  @Override
  public void process(Pattern constraint, ConstraintContext context) {

    context.getValidatorRegistry().add(new ValidatorPattern(constraint.regexp()));
  }

}
