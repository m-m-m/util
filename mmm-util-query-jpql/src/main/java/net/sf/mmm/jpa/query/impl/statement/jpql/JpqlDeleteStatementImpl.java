/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.jpa.query.impl.statement.jpql;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlDeleteStatement;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.statement.AbstractDeleteStatement;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link JpqlDeleteStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlDeleteStatementImpl<E> extends AbstractDeleteStatement<E, JpqlDeleteStatement<E>>
    implements JpqlDeleteStatement<E> {

  private final EntityManager entityManager;

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public JpqlDeleteStatementImpl(EntityManager entityManager, SqlDialect dialect, EntityAlias<E> alias) {
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
  protected long doExecute(String sql, Integer limit) {

    Query query = this.entityManager.createQuery(sql);
    JpqlStatementFactoryImpl.assignParameters(query, this, null, limit);
    return query.executeUpdate();
  }

}
