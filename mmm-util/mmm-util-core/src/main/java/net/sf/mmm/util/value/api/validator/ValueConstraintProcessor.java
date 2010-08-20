/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

import java.lang.reflect.AccessibleObject;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;

/**
 * This is the interface for a processor that
 * {@link #createValidator(AccessibleObject) creates a validator} for a
 * potentially annotated {@link AccessibleObject element}.<br/>
 * There should be one implementation used for this interface that has to be
 * thread-safe.
 * 
 * @see ValueConstraintContainer
 * @see ValueConstraintNumber
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface ValueConstraintProcessor {

  /**
   * This method reads potential <code>ValueConstraint*</code>
   * {@link java.lang.annotation.Annotation}s from the given
   * <code>annotatedElement</code> and creates an according
   * {@link ValueValidator}.
   * 
   * @param annotatedElement is the {@link AccessibleObject} (should be
   *        {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method}
   *        ) that is potentially {@link java.lang.annotation.Annotation
   *        annotated} with a <code>ValueConstraint*</code>.
   * @return the {@link ValueValidator} that validates that the annotated
   *         constraints are met. If no according annotation is present a
   *         dummy-validator will be returned that does nothing.
   */
  ValueValidator<?> createValidator(AccessibleObject annotatedElement);

  /**
   * This method reads potential <code>ValueConstraint*</code>
   * {@link java.lang.annotation.Annotation}s from the given
   * <code>annotatedElement</code> and creates an according
   * {@link ValueValidator}.
   * 
   * @param accessor is the {@link PojoPropertyAccessor accessor} of the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor
   *        property} that is potentially
   *        {@link java.lang.annotation.Annotation annotated} with a
   *        <code>ValueConstraint*</code>.
   * @return the {@link ValueValidator} that validates that the annotated
   *         constraints are met. If no according annotation is present a
   *         dummy-validator will be returned that does nothing.
   */
  ValueValidator<?> createValidator(PojoPropertyAccessor accessor);

}
