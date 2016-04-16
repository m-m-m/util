/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbSelectStatement;
import net.sf.mmm.util.query.api.statenent.jpql.JpqlSelectStatement;
import net.sf.mmm.util.query.base.QueryMode;
import net.sf.mmm.util.query.base.feature.FeatureHavingImpl;
import net.sf.mmm.util.query.base.feature.FeaturePagingImpl;
import net.sf.mmm.util.query.base.path.Alias;
import net.sf.mmm.util.query.base.statement.AbstractSelectStatement;

/**
 * Implementation of {@link OrientDbSelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlSelectStatementImpl<E> extends AbstractSelectStatement<E, JpqlSelectStatement<E>>
    implements JpqlSelectStatement<E> {

  private final EntityManager entityManager;

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias the {@link Alias}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<E> alias) {
    super(dialect, alias);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param paths - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, PropertyPath...)}
   *        .
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<?> alias,
      PropertyPath<?>... paths) {
    super(dialect, alias, paths);
    this.entityManager = entityManager;
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getSqlDialect()}. Typically {@link JpqlDialect#INSTANCE}.
   * @param alias - see {@link #getAlias()}.
   * @param toClass - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   * @param constructorArgs - see
   *        {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, Path...)}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, JpqlDialect dialect, EntityAlias<?> alias,
      Class<E> toClass, PropertyPath<?>... constructorArgs) {
    super(dialect, alias, toClass, constructorArgs);
    this.entityManager = entityManager;
  }

  private void assignParameters(Query query) {

    FeaturePagingImpl paging = feature(FeaturePagingImpl.class);
    query.setFirstResult((int) paging.getOffset());
    query.setMaxResults(paging.getLimit());
    List<Object> variables = getParameters();
    for (int i = 0; i < variables.size(); i++) {
      query.setParameter(i + 1, variables.get(i));
    }
  }

  @Override
  public Object executeQuery(String sql, QueryMode mode) {

    Query query = this.entityManager.createQuery(sql);
    assignParameters(query);
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
