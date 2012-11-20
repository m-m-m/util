/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the interface for a group-by clause (groupby_clause) in JPQL.
 * 
 * @see JpqlQueryBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlOrderByClause<E> extends JpqlFragment<E> {

  /**
   * This method closes this clause and opens an ORDER BY clause.
   * 
   * @see #also(String, SortOrder)
   * 
   * @param property is the primary property to sort the results by. See also
   *        {@link #createPropertyPath(String...)} and {@link #getPropertyPrefix()}.
   * @return the {@link JpqlOrderByClause}.
   */
  JpqlOrderByClause<E> also(String property);

  /**
   * This method closes this clause and opens an ORDER BY clause.
   * 
   * @param property is the primary property to sort the results by. See also
   *        {@link #createPropertyPath(String...)} and {@link #getPropertyPrefix()}.
   * @param order is the {@link SortOrder}.
   * @return the {@link JpqlOrderByClause}.
   */
  JpqlOrderByClause<E> also(String property, SortOrder order);

}
