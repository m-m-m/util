/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

import javax.persistence.TypedQuery;

/**
 * This is the interface for a query. It is created via {@link JpqlBuilder}.<br/>
 * <b>ATTENTION:</b><br/>
 * A query is created lazily. This means that methods such as
 * {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#select()} only create an instance of this
 * interface containing the {@link #getJpqlStatement() query string}. The actual query parsing and execution
 * via JPA happens if an according method like {@link #getSingleResult()} is called. This fact is used for
 * {@link net.sf.mmm.persistence.api.query.jpql.JpqlConditionalExpression#newSubQuery(Class, String) creating
 * sub-queries} that can then be passed as {@link #getJpqlStatement() query string} to another argument of a
 * parent query.
 * 
 * @see javax.persistence.TypedQuery
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SimpleQuery<RESULT> extends JpqlStatement {

  /**
   * This method performs the query and gets the a single result. This method should be used for queries where
   * exactly one matching object is expected.
   * 
   * @see javax.persistence.TypedQuery#getSingleResult()
   * 
   * @return the single result matching the query.
   * @throws RuntimeException if the operation failed. E.g. {@link javax.persistence.NoResultException} or
   *         {@link javax.persistence.NonUniqueResultException} if there is not exactly one single result. See
   *         {@link javax.persistence.TypedQuery#getSingleResult()} for further details.
   */
  RESULT getSingleResult() throws RuntimeException;

  /**
   * {@inheritDoc}
   */
  @Override
  TypedQuery<RESULT> getOrCreateQuery();

}
