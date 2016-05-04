/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.api;

/**
 * This interface represents a search query that is supposed to be created from particular {@link SearchCriteria}. <br>
 * <b>ATTENTION:</b><br>
 * This is a stateful object and therefore NOT thread-safe. Further, each of the methods will cause an (expensive) query
 * to be performed. <br>
 * <b>EXAMPLE:</b><br>
 *
 * <pre>
 * public interface MyEntityDao extends GenericDao&lt;Long, MyEntity> {
 *
 *   {@link SearchQuery}&lt;MyEntity&gt; findByCriteria({@link SearchCriteria MySearchCriteria} criteria);
 *
 * }
 * </pre>
 *
 * @param <HIT> is the generic type of the {@link #search() result} of this query. Typically one of your
 *        {@link net.sf.mmm.util.entity.api.GenericEntity entities} but may also be a transfer object (in case of
 *        constructor-query).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface SearchQuery<HIT> {

  /**
   * This method performs a count query to determine the total number of hits (e.g. in SQL something like
   * "SELECT count(*) FROM ..."). This is a relatively expensive operation.
   *
   * @return the total number of hits for this query in the database.
   */
  long count();

  /**
   * This method performs the actual search to get the {@link SearchResult}. To prevent performance problems the
   * {@link SearchResult} may be {@link SearchResult#isComplete() limited} according to the
   * {@link SearchCriteria#getMaximumHitCount() maximum hit count} as well as by a
   * {@link SearchCriteria#getSearchTimeout() timeout}.
   *
   * @return the {@link SearchResult result} containing the {@link SearchResult#getHits() hits} for the query.
   */
  SearchResult<HIT> search();

}
