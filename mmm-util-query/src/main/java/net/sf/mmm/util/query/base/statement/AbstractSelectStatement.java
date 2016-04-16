/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.SingleQuery;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statement.SelectStatement;
import net.sf.mmm.util.query.base.ListQueryImpl;
import net.sf.mmm.util.query.base.NumberQueryImpl;
import net.sf.mmm.util.query.base.QueryMode;
import net.sf.mmm.util.query.base.SingleQueryImpl;
import net.sf.mmm.util.query.base.feature.FeatureGroupByImpl;
import net.sf.mmm.util.query.base.feature.FeatureOrderByImpl;
import net.sf.mmm.util.query.base.feature.FeaturePagingImpl;
import net.sf.mmm.util.query.base.feature.FeatureWhereImpl;

/**
 * This is the abstract base-implementation of {@link SelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractSelectStatement<E, SELF extends SelectStatement<E, SELF>>
    extends AbstractStatement<E, SELF> implements SelectStatement<E, SELF> {

  private final PropertyPath<?>[] selectionPaths;

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<E> alias) {
    this(dialect, alias, null, (PropertyPath<?>[]) null);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<?> alias, PropertyPath<?>... paths) {
    this(dialect, alias, null, paths);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<?> alias, Class<E> toClass,
      PropertyPath<?>... constructorArgs) {
    super(dialect, alias, toClass);
    this.selectionPaths = constructorArgs;
  }

  /**
   * @return the selectionPaths for
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, net.sf.mmm.util.property.api.path.PropertyPath...)
   *         tuple} or
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, net.sf.mmm.util.query.api.path.Path...)
   *         constructor} queries or {@code null} for
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias) regular select query}
   */
  public PropertyPath<?>[] getSelectionPaths() {

    return this.selectionPaths;
  }

  @Override
  public SELF where(Expression... expressions) {

    feature(FeatureWhereImpl.class).where(expressions);
    return self();
  }

  @Override
  public SELF orderBy(PropertyPath<?> path, SortOrder order) {

    feature(FeatureOrderByImpl.class).orderBy(path, order);
    return self();
  }

  @Override
  public SELF groupBy(PropertyPath<?> path) {

    feature(FeatureGroupByImpl.class).groupBy(path);
    return self();
  }

  @Override
  public SELF limit(int limit) {

    feature(FeaturePagingImpl.class).limit(limit);
    return self();
  }

  @Override
  public SELF offset(long offset) {

    feature(FeaturePagingImpl.class).offset(offset);
    return self();
  }

  @Override
  protected void build(SqlBuilder builder) {

    builder.getBuffer().append(getSqlDialect().from());
    super.build(builder);
  }

  /**
   * Creates a select query with the given {@code SQL} with parameters from the given {@link AbstractStatement
   * statement}.
   *
   * @param sql the SQL to execute.
   * @param mode the {@link QueryMode}.
   * @return the result of the SQL execution.
   */
  public abstract Object executeQuery(String sql, QueryMode mode);

  @Override
  public ListQuery<E> query() {

    String statementSql = getSql();
    StringBuilder sqlBuilder = new StringBuilder(statementSql.length() + 12);
    SqlDialect dialect = getSqlDialect();
    sqlBuilder.append(dialect.select());
    if (this.selectionPaths == null) {
      sqlBuilder.append(dialect.alias(getAlias()));
    } else {
      String separator = null;
      for (PropertyPath<?> path : this.selectionPaths) {
        if (separator == null) {
          separator = dialect.separator();
        } else {
          sqlBuilder.append(separator);
        }
        sqlBuilder.append(dialect.ref(path.getName()));
      }
    }
    sqlBuilder.append(statementSql);
    return new ListQueryImpl<>(this, sqlBuilder.toString(), QueryMode.NORMAL);
  }

  @Override
  public NumberQuery<Long> queryCount() {

    return new NumberQueryImpl<>(this, getSqlDialect().selectCountAll(getAlias()) + getSql());
  }

  /**
   * @see #queryFirst()
   * @see #queryOne()
   *
   * @param mode the {@link QueryMode}.
   * @return the {@link SingleQuery}.
   */
  protected SingleQuery<E> querySingle(QueryMode mode) {

    ListQueryImpl<E> listQuery = new ListQueryImpl<>(this, getSqlDialect().select() + getSql(), mode);
    SingleQueryImpl<E> query = new SingleQueryImpl<>(this, listQuery);
    return query;
  }

  @Override
  public SingleQuery<E> queryFirst() {

    return querySingle(QueryMode.FIRST);
  }

  @Override
  public SingleQuery<E> queryOne() {

    return querySingle(QueryMode.UNIQUE);
  }

}
