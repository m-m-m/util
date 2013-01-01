/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import java.util.List;

import javax.persistence.Query;

import net.sf.mmm.persistence.api.query.ListQuery;
import net.sf.mmm.persistence.api.query.SimpleQuery;
import net.sf.mmm.persistence.api.query.jpql.JpqlFragment;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.search.api.SearchCriteria;
import net.sf.mmm.util.search.api.SearchQuery;
import net.sf.mmm.util.search.api.SearchResult;
import net.sf.mmm.util.search.base.SearchResultBean;

/**
 * This is the implementation of {@link SearchQuery} based on JPA.
 * 
 * @param <HIT> is the generic type of the {@link #search() result} of this query.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class SearchQueryJpa<HIT> implements SearchQuery<HIT> {

  /**
   * The {@link Query#setHint(String, Object) hint} for the {@link SearchCriteria#getSearchTimeout() timeout}.
   */
  private static final String JAVAX_PERSISTENCE_QUERY_TIMEOUT = "javax.persistence.query.timeout";

  /** @see #getSearchCriteria() */
  private final SearchCriteria searchCriteria;

  /** @see #getJpqlFragment() */
  private final JpqlFragment<HIT> jpqlFragment;

  /** @see #getCountQuery() */
  private SimpleQuery<Long> countQuery;

  /** @see #getSearchQuery() */
  private ListQuery<HIT> searchQuery;

  /**
   * The constructor.
   * 
   * @param searchCriteria - see {@link #getSearchCriteria()}
   * @param jpqlFragment - see {@link #getJpqlFragment()}.
   */
  public SearchQueryJpa(SearchCriteria searchCriteria, JpqlFragment<HIT> jpqlFragment) {

    this(searchCriteria, jpqlFragment, null, null);
  }

  /**
   * The constructor.
   * 
   * @param searchCriteria - see {@link #getSearchCriteria()}
   * @param searchQuery - see {@link #getSearchQuery()}.
   * @param countQuery - see {@link #getCountQuery()}.
   */
  public SearchQueryJpa(SearchCriteria searchCriteria, ListQuery<HIT> searchQuery, SimpleQuery<Long> countQuery) {

    this(searchCriteria, null, searchQuery, countQuery);
  }

  /**
   * The constructor.<br/>
   * 
   * If the {@link JpqlFragment} is <code>null</code> then both <code>searchQuery</code> and
   * <code>countQuery</code> need to be set.
   * 
   * @param searchCriteria - see {@link #getSearchCriteria()}
   * @param jpqlFragment - see {@link #getJpqlFragment()}.
   * @param searchQuery - see {@link #getSearchQuery()}.
   * @param countQuery - see {@link #getCountQuery()}.
   */
  public SearchQueryJpa(SearchCriteria searchCriteria, JpqlFragment<HIT> jpqlFragment, ListQuery<HIT> searchQuery,
      SimpleQuery<Long> countQuery) {

    super();
    this.searchCriteria = searchCriteria;
    this.jpqlFragment = jpqlFragment;
    this.searchQuery = searchQuery;
    this.countQuery = countQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long count() {

    applyQuerySettingsAndGetMaxResults(getCountQuery().getOrCreateQuery(), true);
    Long count = getCountQuery().getSingleResult();
    if (count == null) {
      throw new NlsNullPointerException(getCountQuery().getJpqlStatement());
    }
    return count.longValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult<HIT> search() {

    int maxHitCount = applyQuerySettingsAndGetMaxResults(getSearchQuery().getOrCreateQuery(), false);
    List<HIT> resultList = getSearchQuery().getResultList();
    return new SearchResultBean<HIT>(resultList, maxHitCount);
  }

  /**
   * This method applies the query settings ({@link Query#setHint(String, Object) hints} and
   * {@link Query#setMaxResults(int) max results}) and returns the configured value for
   * {@link Query#setMaxResults(int) max results}.
   * 
   * @param query is the query to configure.
   * @param count <code>true</code> if the query is a SELECT COUNT statement, <code>false</code> otherwise.
   * @return the value of {@link #getSearchCriteria()}.{@link SearchCriteria#getMaximumHitCount()
   *         getMaximumHitCount()} or if undefined {@link Integer#MAX_VALUE} as fallback.
   */
  private int applyQuerySettingsAndGetMaxResults(Query query, boolean count) {

    int maxHitCount = Integer.MAX_VALUE;
    if (this.searchCriteria != null) {
      Integer maximumHitCount = this.searchCriteria.getMaximumHitCount();
      if (maximumHitCount != null) {
        maxHitCount = maximumHitCount.intValue();
        // we need to search for one extra hit to determine if the search is complete
        query.setMaxResults(maxHitCount + 1);
      }
      int hitOffset = this.searchCriteria.getHitOffset();
      if (hitOffset > 0) {
        query.setFirstResult(hitOffset);
      }
      Long searchTimeout = this.searchCriteria.getSearchTimeout();
      if (searchTimeout != null) {
        query.setHint(JAVAX_PERSISTENCE_QUERY_TIMEOUT, searchTimeout);
      }
    }
    return maxHitCount;
  }

  /**
   * @return the {@link SearchCriteria} or <code>null</code> if not set.
   */
  public SearchCriteria getSearchCriteria() {

    return this.searchCriteria;
  }

  /**
   * @return the {@link JpqlFragment} or <code>null</code> if not set.
   */
  public JpqlFragment<HIT> getJpqlFragment() {

    return this.jpqlFragment;
  }

  /**
   * @return the {@link SimpleQuery} for {@link #count()}. Will be created using {@link #getJpqlFragment()} on
   *         first call if not already provided.
   */
  protected SimpleQuery<Long> getCountQuery() {

    if (this.countQuery == null) {
      NlsNullPointerException.checkNotNull(JpqlFragment.class, this.jpqlFragment);
      this.countQuery = this.jpqlFragment.selectCount();
    }
    return this.countQuery;
  }

  /**
   * @return the {@link ListQuery} for {@link #search()}. Will be created using {@link #getJpqlFragment()} on
   *         first call if not already provided.
   */
  protected ListQuery<HIT> getSearchQuery() {

    if (this.searchQuery == null) {
      NlsNullPointerException.checkNotNull(JpqlFragment.class, this.jpqlFragment);
      this.searchQuery = this.jpqlFragment.select();
    }
    return this.searchQuery;
  }

}
