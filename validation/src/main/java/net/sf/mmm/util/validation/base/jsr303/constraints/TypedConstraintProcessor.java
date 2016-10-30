/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * This is the interface for a regular typed {@link ConstraintProcessor}.
 *
 * @param <C> the type of the constraint {@link Annotation} {@link #process(Annotation, ConstraintContext) handled} by
 *        this {@link ConstraintProcessor}.
 * @author hohwille
 * @since 7.4.0
 */
public interface TypedConstraintProcessor<C extends Annotation> extends ConstraintProcessor<C> {

  /**
   * @return the {@link Class} reflecting the constraint {@link Annotation} to
   *         {@link #process(Annotation, net.sf.mmm.util.validation.base.jsr303.ConstraintContext) handle}.
   */
  Class<C> getType();

}
