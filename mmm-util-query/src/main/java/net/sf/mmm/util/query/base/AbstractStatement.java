/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.query.api.Statement;
import net.sf.mmm.util.query.api.feature.StatementFeature;
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

  private final Map<Class<? extends StatementFeature>, AbstractFeature> featureMap;

  private SqlBuilder builder;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractStatement(SqlDialect dialect) {
    super();
    this.dialect = dialect;
    this.featureMap = new HashMap<>();
  }

  @Override
  public SqlDialect getSqlDialect() {

    return this.dialect;
  }

  /**
   * @return the name of the source (table, object, class, etc.) to select from.
   */
  protected abstract String getSource();

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

    return AbstractFeature.create(featureClass);
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

    this.builder.addSource(getSource());
    List<AbstractFeature> orderedFeatures = new ArrayList<>(this.featureMap.values());
    Collections.sort(orderedFeatures);
    for (AbstractFeature feature : orderedFeatures) {
      feature.build(sqlBuilder);
    }
  }

  @Override
  public List<Object> getVariables() {

    return getBuilder().getVariables();
  }

  @Override
  public String getSql() {

    return getBuilder().toString();
  }

  @Override
  public String toString() {

    return getBuilder().toString();
  }

}
