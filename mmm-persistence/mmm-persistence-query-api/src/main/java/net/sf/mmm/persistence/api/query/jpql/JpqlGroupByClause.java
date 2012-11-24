/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;


/**
 * This is the interface for a group-by clause (groupby_clause) in JPQL.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlGroupByClause<E> extends JpqlFragment<E> {

  /**
   * This method completes this clause and opens the WHERE clause.
   * 
   * @return the {@link JpqlWhereClause}.
   */
  JpqlHavingClause<E> having();

}
