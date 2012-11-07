/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.search;

/**
 * This interface represents a search query that is supposed to be created from a particular
 * {@link PersistenceSearchCriteria}.<br/>
 * <b>ATTENTION:</b><br/>
 * This is a stateful object and therefore NOT thread-safe. Further, each of the methods will cause an
 * (expensive) database query to be performed.<br/>
 * <b>EXAMPLE:</b><br/>
 * 
 * <pre>
 * public interface MyEntityDao extends {@link net.sf.mmm.persistence.api.GenericDao}&lt;Long, MyEntity> {
 *
 *   {@link PersistenceSearchQuery}&lt;MyEntity> search({@link PersistenceSearchCriteria MySearchCriteria} criteria);
 *
 * }
 * </pre>
 * 
 * @param <HIT> is the generic type of the result of this query. Typically one of your
 *        {@link net.sf.mmm.util.entity.api.GenericEntity entities} but may also be a transfer object (in case
 *        of constructor-query).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface PersistenceSearchQuery<HIT> {

  /**
   * This method performs a count query ("SELECT count(*) FROM ...") to determine the total number of hits.
   * This is a relatively expensive operation.
   * 
   * @return the total number of hits for this query in the database.
   */
  int count();

  /**
   * This method performs the actual search to get the {@link PersistenceSearchResult result}. To prevent
   * performance problems the {@link PersistenceSearchResult result} may be
   * {@link PersistenceSearchResult#isComplete() limited} according to the
   * {@link PersistenceSearchCriteria#getMaximumHitCount() maximum hit count}.
   * 
   * @return the {@link PersistenceSearchResult result} containing the hits for the query.
   */
  PersistenceSearchResult<HIT> search();

}
