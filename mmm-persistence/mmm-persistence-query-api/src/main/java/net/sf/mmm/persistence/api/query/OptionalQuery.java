/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

/**
 * This is the interface for a query.
 * 
 * @see javax.persistence.TypedQuery
 * 
 * @param <RESULT> is the generic type of the {@link #getSingleResult() result}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface OptionalQuery<RESULT> extends SimpleQuery<RESULT> {

  /**
   * This method performs the query and gets the a single, optional result. This method should be used for
   * queries where either one matching object or no match at all is expected.
   * 
   * @return the single result matching the query or <code>null</code> if no object is matching.
   * @throws RuntimeException if the operation faile.d E.g. {@link javax.persistence.NonUniqueResultException}
   *         if more than one object is matching the query.
   */
  RESULT getSingleResultOrNull() throws RuntimeException;

}
