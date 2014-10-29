/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.List;

import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;

/**
 * This is the implementation of {@link ConfiguredSearchIndexerOptions} as
 * simple java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ConfiguredSearchIndexerOptionsBean implements ConfiguredSearchIndexerOptions {

  /** @see #isOverwriteEntries() */
  private boolean overwriteEntries;

  /** @see #isOptimize() */
  private boolean optimize;

  /** @see #getSourceIds() */
  private List<String> sourceIds;

  /** @see #getNonUtfEncoding() */
  private String nonUtfEncoding;

  /**
   * The constructor.
   */
  public ConfiguredSearchIndexerOptionsBean() {

    super();
    this.optimize = true;
    this.sourceIds = null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isOptimize() {

    return this.optimize;
  }

  /**
   * This method sets the {@link #isOptimize() optimize-flag}.
   * 
   * @param optimize is the {@link #isOptimize() optimize-flag} to set.
   */
  public void setOptimize(boolean optimize) {

    this.optimize = optimize;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isOverwriteEntries() {

    return this.overwriteEntries;
  }

  /**
   * This method sets the {@link #isOverwriteEntries() overwriteEntries-flag}. <br>
   * <b>ATTENTION:</b><br>
   * Be careful with overwriting. Your existing entries will be lost. You may
   * loose valuable data.
   * 
   * @param overwriteEntries is the {@link #isOverwriteEntries()
   *        overwriteEntries-flag} to set.
   */
  public void setOverwriteEntries(boolean overwriteEntries) {

    this.overwriteEntries = overwriteEntries;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isOverwriteIndex() {

    if ((this.sourceIds == null) && (this.overwriteEntries)) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public List<String> getSourceIds() {

    return this.sourceIds;
  }

  /**
   * This method sets the {@link #getSourceIds() source-IDs}.
   * 
   * @param sourceIds is the {@link #getSourceIds() source-IDs} to set.
   */
  public void setSourceIds(List<String> sourceIds) {

    this.sourceIds = sourceIds;
  }

  /**
   * {@inheritDoc}
   */
  public String getNonUtfEncoding() {

    return this.nonUtfEncoding;
  }

  /**
   * @param nonUtfEncoding is the nonUtfEncoding to set
   */
  public void setNonUtfEncoding(String nonUtfEncoding) {

    this.nonUtfEncoding = nonUtfEncoding;
  }

}
