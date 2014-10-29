/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.indexer.api.SearchIndexerOptions;

/**
 * This is the implementation of {@link SearchIndexerOptions} as simple java
 * bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerOptionsBean implements SearchIndexerOptions {

  /** @see #isOverwriteIndex() */
  private boolean overwriteIndex;

  /**
   * The constructor.
   */
  public SearchIndexerOptionsBean() {

    super();
    this.overwriteIndex = false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isOverwriteIndex() {

    return this.overwriteIndex;
  }

  /**
   * This method sets the {@link #isOverwriteIndex() overwriteIndex-flag}. <br>
   * <b>ATTENTION:</b><br>
   * Be careful when overwriting the search-index. Your existing index will be
   * lost. You may loose valuable data.
   * 
   * @param overwriteIndex is the {@link #isOverwriteIndex()
   *        overwriteIndex-flag} to set.
   */
  public void setOverwriteIndex(boolean overwriteIndex) {

    this.overwriteIndex = overwriteIndex;
  }

}
