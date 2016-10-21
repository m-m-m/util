/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ConstraintProcessor {

  void process(Annotation constraint, ConstraintContext context);

}
