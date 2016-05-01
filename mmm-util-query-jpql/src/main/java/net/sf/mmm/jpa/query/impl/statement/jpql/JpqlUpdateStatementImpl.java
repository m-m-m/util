/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.jpa.query.impl.statement.jpql;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlUpdateStatement;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.feature.FeatureSetImpl;
import net.sf.mmm.util.query.base.statement.AbstractUpdateStatement;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link JpqlUpdateStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlUpdateStatementImpl<E> extends AbstractUpdateStatement<E, JpqlUpdateStatement<E>>
    implements JpqlUpdateStatement<E> {

  private final EntityManager entityManager;

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public JpqlUpdateStatementImpl(EntityManager entityManager, SqlDialect dialect, EntityAlias<E> alias) {
    super(dialect, alias);
    this.entityManager = entityManager;
  }

  /**
   * @return the {@link EntityManager}.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  @Override
  public long doExecute(String sql, Integer limit) {

    if (feature(FeatureSetImpl.class).getSetExpressionList().isEmpty()) {
      throw new IllegalStateException("No SET expression in SQL: " + sql);
    }
    Query query = this.entityManager.createQuery(sql);
    JpqlStatementFactoryImpl.assignParameters(query, this, null, limit);
    return query.executeUpdate();
  }

}
