/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

/**
 * This is the abstract interface for a segment of a JPQL-query.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract interface JpqlSegment<E> extends JpqlCore {

  /**
   * @return the {@link Class} reflecting the main {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   *         This is typically the result type of this query, e.g. in case of a regular
   *         {@link JpqlFragment#select() select}.
   */
  Class<E> getEntityType();

  /**
   * This method gets the {@link JpqlCore#ALIAS alias} of the {@link #getEntityType() (main) entity}. For
   * example in the query "SELECT foo FROM MyEntity foo ..." this method will return "foo".
   * 
   * @return the alias of the {@link #getEntityType() (main) entity}.
   */
  String getEntityAlias();

}
