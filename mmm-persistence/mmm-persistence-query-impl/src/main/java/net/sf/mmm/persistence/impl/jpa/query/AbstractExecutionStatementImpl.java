/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.persistence.api.query.JpqlStatement} for
 * statements that represent untyped {@link Query queries} for DELETE or UPDATE.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract class AbstractExecutionStatementImpl extends AbstractStatementImpl {

  /** @see #getOrCreateQuery() */
  private Query query;

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public AbstractExecutionStatementImpl(String jpqlStatement, AbstractJpqlContext context) {

    super(jpqlStatement, context);
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param entityManager - see {@link #getEntityManager()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public AbstractExecutionStatementImpl(String jpqlStatement, EntityManager entityManager, List<Object> parameters) {

    super(jpqlStatement, entityManager, parameters);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query getOrCreateQuery() {

    if (this.query == null) {
      this.query = createQuery(getJpqlStatement());
    }
    return this.query;
  }

}
