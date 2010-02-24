/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to annotate a property (field or setter) of the type
 * {@link Number} or one of its sub-types that is also annotated with
 * {@link CliOption} or {@link CliArgument}. It allows to define a constraint
 * for the range of the {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliConstraintNumber {

  /**
   * The minimum allowed value. Default is 0, use {@link Double#MIN_VALUE} to
   * allow unbounded negative values.
   */
  double min() default 0;

  /**
   * The maximum allowed value.
   */
  double max() default Double.MAX_VALUE;

}
