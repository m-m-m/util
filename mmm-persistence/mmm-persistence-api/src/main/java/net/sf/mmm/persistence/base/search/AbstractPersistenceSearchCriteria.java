/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.search;

import net.sf.mmm.persistence.api.search.PersistenceSearchCriteria;

/**
 * This is the abstract base implementation of {@link PersistenceSearchCriteria}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractPersistenceSearchCriteria implements PersistenceSearchCriteria {

  /** UID for serialization. */
  private static final long serialVersionUID = -8080276978548478955L;

  /** @see #getMaximumHitCount() */
  private Integer maximumHitCount;

  /** @see #getHitOffset() */
  private int hitOffset;

  /** @see #getSearchTimeout() */
  private Integer searchTimeout;

  /**
   * The constructor.
   */
  public AbstractPersistenceSearchCriteria() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getMaximumHitCount() {

    return this.maximumHitCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHitOffset() {

    return this.hitOffset;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getSearchTimeout() {

    return this.searchTimeout;
  }

  /**
   * @param maximumHitCount is the new value of {@link #getMaximumHitCount()}.
   */
  public void setMaximumHitCount(Integer maximumHitCount) {

    this.maximumHitCount = maximumHitCount;
  }

  /**
   * @param hitOffset is the new value of {@link #getHitOffset()}.
   */
  public void setHitOffset(int hitOffset) {

    this.hitOffset = hitOffset;
  }

  /**
   * @param searchTimeout is the new value of {@link #getSearchTimeout()}.
   */
  public void setSearchTimeout(Integer searchTimeout) {

    this.searchTimeout = searchTimeout;
  }

}
