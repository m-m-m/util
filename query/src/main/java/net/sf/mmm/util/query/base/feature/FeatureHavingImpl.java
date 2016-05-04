/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.feature.FeatureHaving;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeatureHavingImpl extends AbstractFeature implements FeatureHaving<FeatureHavingImpl> {

  private Expression having;

  /**
   * The constructor.
   */
  public FeatureHavingImpl() {
    super(SORT_INDEX_HAVING);
  }

  @Override
  public FeatureHavingImpl having(Expression... expressions) {

    Expression expression = combine(this.having, Conjunction.AND, expressions);
    if (expression.isConstant() && !expression.evaluate()) {
      throw new IllegalArgumentException("Expression can never match!");
    }
    this.having = expression;
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.having == null) {
      return;
    }
    if (this.having.isConstant()) {
      if (!this.having.evaluate()) {
        throw new IllegalStateException();
      }
    } else {
      builder.append(getDialect().having());
      builder.addExpression(this.having);
    }
  }

}
