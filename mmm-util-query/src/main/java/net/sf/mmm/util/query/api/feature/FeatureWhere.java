/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.query.api.expression.Expression;

/**
 * This is the abstract interface for a {@link net.sf.mmm.util.query.api.statement.Statement} allowing a
 * {@link #where(Expression...) WHERE clause}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureWhere<SELF extends FeatureWhere<SELF>> extends StatementFeature {

  /**
   * Adds the given {@link Expression}s as filter criteria to the WHERE-clause. Multiple invocations will combine
   * {@link Expression}s with {@link net.sf.mmm.util.lang.api.Conjunction#AND logical AND} but it is preferred to use a
   * single invocation.
   *
   * @param expressions the {@link Expression}s to add.
   * @return this query instance for fluent API calls.
   */
  SELF where(Expression... expressions);

}
