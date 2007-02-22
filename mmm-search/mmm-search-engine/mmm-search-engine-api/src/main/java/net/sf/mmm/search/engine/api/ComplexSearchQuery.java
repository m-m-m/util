/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface of a query that combines other queries.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComplexSearchQuery extends SearchQuery {

  /**
   * This method adds the given <code>subQuery</code> as required sub-query. A
   * {@link net.sf.mmm.search.api.SearchEntry entry} has to match the
   * <code>subQuery</code> in order to match this complex query.
   * 
   * @param subQuery
   *        is the sub-query to add.
   */
  void addRequiredQuery(SearchQuery subQuery);

  /**
   * This method adds the given <code>subQuery</code> as optional sub-query. A
   * {@link net.sf.mmm.search.api.SearchEntry entry} should match the
   * <code>subQuery</code> in order to match this complex query. Else if other
   * sub-queries match, this complex query can still match with a lower
   * {@link SearchHit#getScore() score}.
   * 
   * @param subQuery
   *        is the sub-query to add.
   */
  void addOptionalQuery(SearchQuery subQuery);

  /**
   * This method adds the given <code>subQuery</code> as excluding sub-query.
   * A {@link net.sf.mmm.search.api.SearchEntry entry} must NOT match the
   * <code>subQuery</code> in order to match this complex query.
   * 
   * @param subQuery
   *        is the sub-query to add.
   */
  void addExcludingQuery(SearchQuery subQuery);

  /**
   * This method gets the number of sub-queries that have been added to this
   * query.
   * 
   * @return the number of sub-queries.
   */
  int getSubQueryCount();

}
