/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

/**
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has a type <em>compatible</em> with {@link Number}. Here compatible
 * means that its
 * {@link net.sf.mmm.util.reflect.api.ReflectionUtil#getNonPrimitiveType(Class)
 * non-primitive type} is {@link Number} or a sub-class of {@link Number}.<br/>
 * If this annotation is used for a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has a {@link ValueConstraintContainer container}-type, then it applies
 * to all elements of that container.
 * 
 * @see ValueConstraintProcessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public @interface ValueConstraintNumber {

  /**
   * The minimum allowed
   * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object)
   * size}. This value may NOT be negative.
   */
  int minimum() default 0;

  /**
   * The maximum allowed
   * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object)
   * size}.
   */
  int maximum() default Integer.MAX_VALUE;

}
