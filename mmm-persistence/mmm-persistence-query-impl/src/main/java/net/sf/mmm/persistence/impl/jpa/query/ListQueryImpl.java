/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.mmm.persistence.api.query.ListQuery;

/**
 * This is the implementation of {@link ListQuery} based on {@link OptionalQueryImpl}.
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ListQueryImpl<RESULT> extends OptionalQueryImpl<RESULT> implements ListQuery<RESULT> {

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param resultType - see {@link #getResultType()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public ListQueryImpl(String jpqlStatement, Class<RESULT> resultType, AbstractJpqlContext context) {

    super(jpqlStatement, resultType, context);
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param resultType - see {@link #getResultType()}.
   * @param entityManager - see {@link #getParameters()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public ListQueryImpl(String jpqlStatement, Class<RESULT> resultType, EntityManager entityManager, List<Object> parameters) {

    super(jpqlStatement, resultType, entityManager, parameters);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<RESULT> getResultList() {

    return getOrCreateQuery().getResultList();
  }

}
