/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class FeatureWhereImpl extends AbstractFeature implements FeatureWhere<FeatureWhereImpl> {

  private Expression where;

  /**
   * The constructor.
   */
  public FeatureWhereImpl() {
    super(SORT_INDEX_WHERE);
  }

  @Override
  public FeatureWhereImpl where(Expression... expressions) {

    Expression expression = combine(this.where, Conjunction.AND, expressions);
    if (expression.isConstant() && !expression.evaluate()) {
      throw new IllegalArgumentException("Expression can never match!");
    }
    this.where = expression;
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.where == null) {
      return;
    }
    if (this.where.isConstant()) {
      if (!this.where.evaluate()) {
        throw new IllegalStateException();
      }
    } else {
      builder.getBuffer().append(getDialect().where());
      builder.addExpression(this.where);
    }
  }

}
