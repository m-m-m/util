/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.query.DeleteStatement;

/**
 * This is the abstract base-implementation of {@link DeleteStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractDeleteStatement<E, SELF extends AbstractDeleteStatement<E, SELF>>
    extends AbstractModifyStatement<E, SELF> implements DeleteStatement<E, SELF> {

  private Expression where;

  private long limit;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractDeleteStatement(SqlDialect dialect) {
    super(dialect);
  }

  @Override
  public SELF where(Expression... expressions) {

    this.where = combine(this.where, Conjunction.AND, expressions);
    return self();
  }

  @Override
  public SELF limit(long newLimit) {

    this.limit = newLimit;
    return self();
  }

  @Override
  protected void build(SqlBuilder builder) {

    builder.addDeleteFrom(getSource());
    builder.addWhere(this.where);
    builder.addPaging(0, this.limit);
  }

}
