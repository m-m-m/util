/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.base;

import net.sf.mmm.util.search.api.SearchCriteria;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * This is the abstract base implementation of {@link SearchCriteria}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractSearchCriteria extends AbstractTransferObject implements SearchCriteria {

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
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.hitOffset;
    result = prime * result + ((this.maximumHitCount == null) ? 0 : this.maximumHitCount.hashCode());
    result = prime * result + (this.readOnly ? 1231 : 1237);
    result = prime * result + ((this.searchTimeout == null) ? 0 : this.searchTimeout.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractSearchCriteria other = (AbstractSearchCriteria) obj;
    if (this.hitOffset != other.hitOffset) {
      return false;
    }
    if (this.maximumHitCount == null) {
      if (other.maximumHitCount != null) {
        return false;
      }
    } else if (!this.maximumHitCount.equals(other.maximumHitCount)) {
      return false;
    }
    if (this.readOnly != other.readOnly) {
      return false;
    }
    if (this.searchTimeout == null) {
      if (other.searchTimeout != null) {
        return false;
      }
    } else if (!this.searchTimeout.equals(other.searchTimeout)) {
      return false;
    }
    return true;
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

  /**
   * Limits the {@link #getMaximumHitCount() maximum hit count} by the given <code>limit</code>. If current
   * {@link #getMaximumHitCount() maximum hit count} is <code>null</code> or greater than the given
   * <code>limit</code>, the value is replaced by <code>limit</code>.
   *
   * @param limit is the maximum allowed value for {@link #getMaximumHitCount() maximum hit count}.
   * @since 6.0.0
   */
  public void limitMaximumHitCount(int limit) {

    Integer max = getMaximumHitCount();
    if ((max == null) || (max.intValue() > limit)) {
      setMaximumHitCount(Integer.valueOf(limit));
    }
  }
}
