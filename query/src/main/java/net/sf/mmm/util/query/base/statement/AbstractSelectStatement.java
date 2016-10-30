/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.function.Function;

import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.SingleQuery;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.ComparablePath;
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
 * @param <T> the generic type of the {@link #execute(String, QueryMode) internal results}. See {@link #getMapper()}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractSelectStatement<E, SELF extends SelectStatement<E, SELF>, T>
    extends AbstractStatement<E, SELF> implements SelectStatement<E, SELF> {

  private final PropertyPath<?>[] selectionPaths;

  private final Function<T, E> mapper;

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<E> alias, Function<T, E> mapper) {
    this(dialect, alias, mapper, null, (PropertyPath<?>[]) null);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<?> alias, Function<T, E> mapper,
      PropertyPath<?>... paths) {
    this(dialect, alias, mapper, null, paths);
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public AbstractSelectStatement(SqlDialect dialect, EntityAlias<?> alias, Function<T, E> mapper, Class<E> toClass,
      PropertyPath<?>... constructorArgs) {
    super(dialect, alias, toClass);
    this.mapper = mapper;
    this.selectionPaths = constructorArgs;
  }

  /**
   * @return the {@link Function} to {@link Function#apply(Object) map} the original query results to the external
   *         result type. May be {@code null} for the identity function if {@literal <T>=<E>}.
   */
  public Function<T, E> getMapper() {

    return this.mapper;
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
  public SELF orderBy(ComparablePath<?> path, SortOrder order) {

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
  protected void buildStart(SqlBuilder builder) {

    builder.getBuffer().append(getDialect().from());
    super.buildStart(builder);
  }

  /**
   * Creates a select query with the given {@code SQL} and the given {@link QueryMode}.
   *
   * @param sql the SQL to execute.
   * @param mode the {@link QueryMode}.
   * @return the result of the SQL execution.
   */
  public Object execute(String sql, QueryMode mode) {

    FeaturePagingImpl paging = feature(FeaturePagingImpl.class);
    Integer limit;
    if (mode == QueryMode.FIRST) {
      limit = Integer.valueOf(1);
    } else if (mode == QueryMode.UNIQUE) {
      limit = Integer.valueOf(2);
    } else {
      limit = paging.getLimit();
    }
    return doExecute(sql, mode, paging.getOffset(), limit);
  }

  /**
   * Creates a select query with the given {@code SQL} and the given arguments.
   *
   * @param sql the SQL to execute.
   * @param mode the {@link QueryMode}.
   * @param offset the {@link net.sf.mmm.util.query.api.feature.FeaturePaging#offset(long) offset}.
   * @param limit the {@link net.sf.mmm.util.query.api.feature.FeaturePaging#limit(int) limit}.
   * @return the result of the SQL execution.
   */
  protected abstract Object doExecute(String sql, QueryMode mode, Long offset, Integer limit);

  @Override
  public ListQuery<E> query() {

    String statementSql = getSql();
    StringBuilder sqlBuilder = new StringBuilder(statementSql.length() + 12);
    SqlDialect dialect = getDialect();
    sqlBuilder.append(dialect.select());
    if (this.selectionPaths == null) {
      sqlBuilder.append(dialect.alias(getAlias(), true));
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
    return new ListQueryImpl<>(this, sqlBuilder.toString(), QueryMode.NORMAL, this.mapper);
  }

  @Override
  public NumberQuery<Long> queryCount() {

    return new NumberQueryImpl<>(this, getDialect().selectCountAll(getAlias()) + getSql());
  }

  /**
   * @see #queryFirst()
   * @see #queryOne()
   *
   * @param mode the {@link QueryMode}.
   * @return the {@link SingleQuery}.
   */
  protected SingleQuery<E> querySingle(QueryMode mode) {

    String sql = createSqlSingleQuery(mode);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ListQuery<T> listQuery = new ListQueryImpl(this, sql, mode, null);
    SingleQueryImpl<E, T> query = new SingleQueryImpl<>(this, listQuery, mode, this.mapper);
    return query;
  }

  /**
   * @param mode the {@link QueryMode} for a single SELECT query what is {@link QueryMode#FIRST} or
   *        {@link QueryMode#UNIQUE}.
   * @return the complete {@link #getSql() SQL} for a single query.
   */
  protected String createSqlSingleQuery(QueryMode mode) {

    if (mode == QueryMode.UNIQUE) {
      return getDialect().selectDistinct() + getSql();
    } else {
      return getDialect().select() + getSql();
    }
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
