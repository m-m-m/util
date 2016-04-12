/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.statement.UpdateStatement;
import net.sf.mmm.util.query.base.feature.FeaturePagingImpl;
import net.sf.mmm.util.query.base.feature.FeatureWhereImpl;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * This is the abstract base-implementation of {@link UpdateStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractUpdateStatement<E, SELF extends UpdateStatement<E, SELF>>
    extends AbstractStoreStatement<E, SELF> implements UpdateStatement<E, SELF> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public AbstractUpdateStatement(SqlDialect dialect, Alias<E> alias) {
    super(dialect, alias);
  }

  @Override
  public SELF where(Expression... expressions) {

    feature(FeatureWhereImpl.class).where(expressions);
    return self();
  }

  @Override
  public SELF limit(int limit) {

    feature(FeaturePagingImpl.class).limit(limit);
    return self();
  }

  @Override
  protected void build(SqlBuilder builder) {

    builder.getBuffer().append(getSqlDialect().update());
    super.build(builder);
  }

}
