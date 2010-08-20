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
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that is an {@link CliOption option} or {@link CliArgument argument} and has a
 * <em>container type</em>. A container is an
 * {@link Class#isAssignableFrom(Class) array}, a {@link java.util.Collection}
 * or a {@link java.util.Map}. It allows to define a constraint for the range of
 * the
 * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object)
 * size} of the container.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliConstraintContainer {

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
