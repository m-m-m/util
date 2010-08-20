/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

/**
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has a <em>container type</em>. A container is an
 * {@link Class#isAssignableFrom(Class) array}, a {@link java.util.Collection}
 * or a {@link java.util.Map}. It allows to define a constraint for the range of
 * the
 * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object)
 * size} of the container.<br/>
 * Please note that is annotation can be combined with other
 * <code>ValueConstraint*</code> annotations that will apply to all elements in
 * the container.
 * 
 * @see ValueConstraintProcessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public @interface ValueConstraintContainer {

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
