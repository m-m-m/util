/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the abstract interface for {@link #groupBy(String...) GROUP BY} support.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlGroupBySupport<E> extends JpqlFragment<E> {

  /**
   * This method closes this clause and opens a {@link #JPQL_GROUP_BY} clause.
   * 
   * @param groupByItems are the {@link #PROPERTY properties} or items (singleValuedPathExpression or
   *        identificationVariable) to group by.
   * @return the {@link JpqlGroupByClause}.
   */
  JpqlGroupByClause<E> groupBy(String... groupByItems);

  /**
   * This method closes this clause and opens a {@link #JPQL_GROUP_BY} clause.
   * 
   * @param properties are the {@link #PROPERTY properties} to group by.
   * @return the {@link JpqlGroupByClause}.
   */
  JpqlGroupByClause<E> groupBy(TypedProperty<?>... properties);

}
