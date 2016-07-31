/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

/**
 * This {@link Enum} defines the available ways to simplify and evaluate
 * {@link net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoice choice format expressions}.
 *
 * @author hohwille
 * @since 7.3.0
 */
public enum ChoiceEvaluation {

  /** Resolve by evaluating the '(else)' part. If not present omitt entire argument. */
  asElse,

  /** Resolve by evaluating with {@code null} value. */
  asNull

}
