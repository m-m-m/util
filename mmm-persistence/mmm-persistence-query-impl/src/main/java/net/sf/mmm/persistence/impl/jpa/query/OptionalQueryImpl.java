/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;

import net.sf.mmm.persistence.api.query.OptionalQuery;

/**
 * This is the implementation of {@link OptionalQuery} based on {@link SimpleQueryImpl}.
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class OptionalQueryImpl<RESULT> extends SimpleQueryImpl<RESULT> implements OptionalQuery<RESULT> {

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param resultType - see {@link #getResultType()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public OptionalQueryImpl(String jpqlStatement, Class<RESULT> resultType, AbstractJpqlContext context) {

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
  public OptionalQueryImpl(String jpqlStatement, Class<RESULT> resultType, EntityManager entityManager,
      List<Object> parameters) {

    super(jpqlStatement, resultType, entityManager, parameters);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RESULT getSingleResultOrNull() throws RuntimeException {

    List<RESULT> resultList = getOrCreateQuery().getResultList();
    if (resultList.size() > 1) {
      throw new NonUniqueResultException(getJpqlStatement());
    }
    if (resultList.isEmpty()) {
      return null;
    } else {
      return resultList.get(0);
    }
  }
}
