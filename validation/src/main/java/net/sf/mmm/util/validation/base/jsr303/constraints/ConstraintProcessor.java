/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303.constraints;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.validation.base.jsr303.ConstraintContext;

/**
 * This is the interface for a processor of a constraint {@link Annotation}.
 *
 * @param <C> the type of the constraint {@link Annotation} {@link #process(Annotation, ConstraintContext) handled} by
 *        this {@link ConstraintProcessor}.
 * @author hohwille
 * @since 8.0.0
 */
public interface ConstraintProcessor<C extends Annotation> {

  /**
   * @param constraint the constraint {@link Annotation} to process.
   * @param context the {@link ConstraintContext}.
   */
  void process(C constraint, ConstraintContext context);

}
