/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.base;

import net.sf.mmm.util.search.api.SearchCriteria;

/**
 * This is the abstract base implementation of {@link SearchCriteria}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractSearchCriteria implements SearchCriteria {

  /** UID for serialization. */
  private static final long serialVersionUID = -8080276978548478955L;

  /** @see #getMaximumHitCount() */
  private Integer maximumHitCount;

  /** @see #getHitOffset() */
  private int hitOffset;

  /** @see #getSearchTimeout() */
  private Long searchTimeout;

  /** @see #isReadOnly() */
  private boolean readOnly;

  /**
   * The constructor.
   */
  public AbstractSearchCriteria() {

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
  public Long getSearchTimeout() {

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
  public void setSearchTimeout(Long searchTimeout) {

    this.searchTimeout = searchTimeout;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadOnly() {

    return this.readOnly;
  }

  /**
   * @param readOnly is the new value of {@link #isReadOnly()}.
   */
  public void setReadOnly(boolean readOnly) {

    this.readOnly = readOnly;
  }

}
