/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the interface for an order-by clause (orderby_clause) in JPQL.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlOrderByClause<E> extends JpqlFragment<E> {

  /**
   * This method adds more properties to order the results by.
   * 
   * @see #also(String, SortOrder)
   * 
   * @param property is an additional {@link JpqlCore#PROPERTY property} to sort the results by.
   * @return the {@link JpqlOrderByClause}.
   */
  JpqlOrderByClause<E> also(String property);

  /**
   * This method adds more properties to order the results by.
   * 
   * @param property is an additional {@link JpqlCore#PROPERTY property} to sort the results by.
   * @param order is the {@link SortOrder}.
   * @return the {@link JpqlOrderByClause}.
   */
  JpqlOrderByClause<E> also(String property, SortOrder order);

}
