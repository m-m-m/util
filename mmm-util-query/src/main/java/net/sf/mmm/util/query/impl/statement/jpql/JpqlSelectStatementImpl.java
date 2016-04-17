/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.impl.statement.jpql;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statenent.jpql.JpqlSelectStatement;
import net.sf.mmm.util.query.base.QueryMode;
import net.sf.mmm.util.query.base.feature.FeatureHavingImpl;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;
import net.sf.mmm.util.query.base.statement.SqlDialect;
import net.sf.mmm.util.query.base.statement.jpql.JpqlDialect;

/**
 * Implementation of {@link JpqlSelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <T> the generic type of the {@link #execute(String, QueryMode) internal results}. See {@link #getMapper()}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlSelectStatementImpl<E, T> extends AbstractSelectStatement<E, JpqlSelectStatement<E>, T>
    implements JpqlSelectStatement<E> {

  private final EntityManager entityManager;

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<E> alias,
      Function<T, E> mapper) {
    super(dialect, alias, mapper);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, SqlDialect dialect, EntityAlias<?> alias,
      Function<T, E> mapper, Class<E> toClass, PropertyPath<?>... constructorArgs) {
    super(dialect, alias, mapper, toClass, constructorArgs);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statenent.jpql.JpqlStatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, SqlDialect dialect, EntityAlias<?> alias,
      Function<T, E> mapper, PropertyPath<?>... paths) {
    super(dialect, alias, mapper, paths);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<?> alias,
      Function<T, E> mapper, PropertyPath<?>... paths) {
    super(dialect, alias, mapper, paths);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param mapper - see {@link #getMapper()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<?> alias,
      Function<T, E> mapper, Class<E> toClass, PropertyPath<?>... constructorArgs) {
    super(dialect, alias, mapper, toClass, constructorArgs);
    this.entityManager = entityManager;
  }

  /**
   * @return the {@link EntityManager}.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  @Override
  protected Object doExecute(String sql, QueryMode mode, Long offset, Integer limit) {

    Query query = this.entityManager.createQuery(sql);
    JpqlStatementFactoryImpl.assignParameters(query, this, offset, limit);
    if (mode == QueryMode.NORMAL) {
      return query.getResultList();
    } else {
      return query.getSingleResult();
    }
  }

  @Override
  public JpqlSelectStatement<E> having(Expression... expressions) {

    feature(FeatureHavingImpl.class).having(expressions);
    return self();
  }

}
