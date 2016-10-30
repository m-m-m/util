/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.expression;

import net.sf.mmm.util.lang.api.Conjunction;

/**
 * This enum controls how {@link Expression}s are surrounded with brackets when formatted.
 *
 * @author hohwille
 * @since 8.4.0
 */
public enum Bracketing {

  /** Put all {@link Expression}s in brackets. */
  ALL,

  /** Put all inner {@link Expression}s in brackets. */
  INNER,

  /**
   * Put only {@link Expression}s in brackets where required (e.g. because {@link Conjunction#OR Or} is weaker than
   * {@link Conjunction#AND And}).
   */
  MINIMAL

}
