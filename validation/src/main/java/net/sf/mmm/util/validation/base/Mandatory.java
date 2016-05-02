/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This is a {@link Constraint} for a mandatory field. It is similar to
 * {@link javax.validation.constraints.NotNull} but with an appropriate {@link #message() message} and also
 * considering empty {@link String}s, {@link java.util.Collection}s or {@link java.util.Map}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MandatoryValidator.class)
public @interface Mandatory {

  /** @see Constraint */
  String message() default "{net.sf.mmm.util.validation.base.Mandatory.message}";

  /** @see Constraint */
  Class<?>[] groups() default {};

  /** @see Constraint */
  Class<? extends Payload>[] payload() default {};

}
