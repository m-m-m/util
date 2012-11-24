/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import net.sf.mmm.persistence.api.query.SimpleQuery;

/**
 * This is the implementation of {@link SimpleQuery} using JPA.
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SimpleQueryImpl<RESULT> extends AbstractStatementImpl implements SimpleQuery<RESULT> {

  /** @see #getResultType() */
  private Class<RESULT> resultType;

  /** @see #getOrCreateQuery() */
  private TypedQuery<RESULT> query;

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param resultType - see {@link #getResultType()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public SimpleQueryImpl(String jpqlStatement, Class<RESULT> resultType, AbstractJpqlContext context) {

    super(jpqlStatement, context);
    this.resultType = resultType;
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param resultType - see {@link #getResultType()}.
   * @param entityManager - see {@link #getParameters()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public SimpleQueryImpl(String jpqlStatement, Class<RESULT> resultType, EntityManager entityManager,
      List<Object> parameters) {

    super(jpqlStatement, entityManager, parameters);
    this.resultType = resultType;
  }

  /**
   * @return the resultType
   */
  public Class<RESULT> getResultType() {

    return this.resultType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<RESULT> getOrCreateQuery() {

    if (this.query == null) {
      this.query = createTypedQuery(getJpqlStatement(), this.resultType);
    }
    return this.query;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RESULT getSingleResult() throws RuntimeException {

    return getOrCreateQuery().getSingleResult();
  }

}
