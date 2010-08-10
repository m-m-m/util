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
 * {@link java.util.List} or {@link java.util.Set} that is also annotated with
 * {@link CliArgument}. It allows to define a constraint for the range of the
 * {@link java.util.Collection#size() size} of the {@link java.util.Collection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliConstraintCollection {

  /**
   * The minimum allowed
   * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object)
   * size}. This value should be positive.
   */
  int min() default 1;

  /**
   * The maximum allowed value.
   */
  int max() default Integer.MAX_VALUE;

  /**
   * The style how to map collections.
   */
  CliCollectionStyle style() default CliCollectionStyle.MULTIPLE_OCCURRENCE;

}
