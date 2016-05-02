/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.search.api.SearchResult;

/**
 * This is the implementation of {@link SearchResult}.
 *
 * @param <HIT> is the type of the individual items contained in this result.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class SearchResultBean<HIT> implements SearchResult<HIT> {

  /** UID for serialization. */
  private static final long serialVersionUID = -7872658532720661708L;

  /** @see #isComplete() */
  private boolean complete;

  /** @see #getHits() */
  private List<HIT> hits;

  /**
   * The constructor.
   */
  public SearchResultBean() {

    super();
  }

  /**
   * The constructor.
   *
   * @param hitList is the {@link #getHits() hit-list} that may return more hits than the given
   *        {@code maximumHitCount}. In that case the {@link #getHits() hits} will be created as a truncated copy
   *        of the given {@code hitList}. Otherwise {@link #getHits()} will return the given {@code hitList}.
   * @param maximumHitCount is the {@link net.sf.mmm.util.search.api.SearchCriteria#getMaximumHitCount() maximum
   *        hit-count}.
   */
  public SearchResultBean(List<HIT> hitList, int maximumHitCount) {

    super();
    if (hitList.size() <= maximumHitCount) {
      this.hits = hitList;
      this.complete = true;
    } else {
      this.hits = new ArrayList<>(maximumHitCount);
      this.hits.addAll(hitList.subList(0, maximumHitCount + 1));
      this.complete = false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isComplete() {

    return this.complete;
  }

  /**
   * @param complete is the complete to set
   */
  public void setComplete(boolean complete) {

    this.complete = complete;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<HIT> getHits() {

    return this.hits;
  }

  /**
   * @param hits are the hits to set.
   */
  public void setHits(List<HIT> hits) {

    this.hits = hits;
  }

}
