/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.Statement;
import net.sf.mmm.util.query.base.feature.AbstractFeature;

/**
 * This is the abstract base-implementation of {@link Statement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractStatement<E, SELF extends Statement<E, SELF>> implements Statement<E, SELF> {

  private final SqlDialect dialect;

  private final EntityAlias<?> alias;

  private final Class<E> resultClass;

  private final Map<Class<? extends StatementFeature>, AbstractFeature> featureMap;

  private final List<EntityAlias<?>> aliases;

  private SqlBuilder builder;

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public AbstractStatement(SqlDialect dialect, EntityAlias<E> alias) {
    this(dialect, alias, alias.getType());
  }

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   * @param resultClass - see {@link #getResultClass()}.
   */
  public AbstractStatement(SqlDialect dialect, EntityAlias<?> alias, Class<E> resultClass) {
    super();
    this.dialect = dialect;
    this.alias = alias;
    this.resultClass = resultClass;
    this.featureMap = new HashMap<>();
    this.aliases = new ArrayList<>();
    this.aliases.add(alias);
  }

  @Override
  public SqlDialect getSqlDialect() {

    return this.dialect;
  }

  /**
   * @return the {@link EntityAlias} to select from. For regular {@link Statement}s this will be <code>
   *         {@link EntityAlias}{@literal <E>}</code> but for special queries such as
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, net.sf.mmm.util.property.api.path.PropertyPath...)
   *         tuple} or
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, net.sf.mmm.util.query.api.path.Path...)
   *         constructor} queries this is not the
   */
  public EntityAlias<?> getAlias() {

    return this.alias;
  }

  /**
   * @return the {@link Class} reflecting the result or destination of this {@link Statement}. For a regular
   *         {@link Statement} this is {@link #getAlias()}.{@link EntityAlias#getType() getType()} but e.g. for
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, net.sf.mmm.util.query.api.path.Path...)
   *         constructor} queries this will the top-level class to return.
   */
  protected Class<E> getResultClass() {

    return this.resultClass;
  }

  /**
   * @param <F> the generic type of the requested {@link AbstractFeature feature}.
   * @param featureClass the {@link Class} reflecting the requested {@link AbstractFeature feature}.
   * @return the requested {@link AbstractFeature feature}.
   */
  protected <F extends AbstractFeature> F feature(Class<F> featureClass) {

    @SuppressWarnings("unchecked")
    F feature = (F) this.featureMap.get(featureClass);
    if (feature == null) {
      feature = createFeature(featureClass);
      this.featureMap.put(featureClass, feature);
    }
    return feature;
  }

  /**
   * @param <F> the generic type of the {@link AbstractFeature feature}.
   * @param featureClass the {@link Class} reflecting the {@link AbstractFeature feature}.
   * @return a new instance of the specified {@link AbstractFeature feature}.
   */
  protected <F extends AbstractFeature> F createFeature(Class<F> featureClass) {

    F feature = AbstractFeature.create(featureClass);
    feature.setStatement(this);
    return feature;
  }

  /**
   * @return this query instance for fluent API calls.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    this.builder = null;
    return (SELF) this;
  }

  /**
   * @return the {@link SqlBuilder}.
   */
  protected SqlBuilder getBuilder() {

    if (this.builder == null) {
      this.builder = createSqlBuilder();
      build(this.builder);
    }
    return this.builder;
  }

  /**
   * @return a new instance of {@code SqlContext}.
   */
  protected SqlBuilder createSqlBuilder() {

    return new SqlBuilder(this.dialect);
  }

  /**
   * @param sqlBuilder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  protected void build(SqlBuilder sqlBuilder) {

    this.builder.addAlias(getAlias());
    List<AbstractFeature> orderedFeatures = new ArrayList<>(this.featureMap.values());
    Collections.sort(orderedFeatures);
    for (AbstractFeature feature : orderedFeatures) {
      feature.build(sqlBuilder);
    }
  }

  @Override
  public List<Object> getParameters() {

    return getBuilder().getParameters();
  }

  @Override
  public String getSql() {

    return getBuilder().getSql();
  }

  @Override
  public String toString() {

    return getSql();
  }

}
