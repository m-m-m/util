/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

/**
 * This is the interface for a where-clause of a JPQL query.
 * 
 * @see JpqlQueryBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlWhereClause<E> extends JpqlConditionalExpression<E, JpqlWhereClause<E>> {

  /**
   * This method closes this clause and opens a GROUPED BY clause.
   * 
   * @return the {@link JpqlGroupByClause}.
   */
  JpqlGroupByClause<E> groupBy();

}
