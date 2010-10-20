/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import net.sf.mmm.search.engine.api.config.SearchEngineOptions;

/**
 * This is the implementation of {@link SearchEngineOptions} as simple java
 * bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchEngineOptionsBean implements SearchEngineOptions {

  /** @see #isAutoRefresh() */
  private boolean autoRefresh;

  /**
   * The constructor.
   */
  public SearchEngineOptionsBean() {

    super();
    this.autoRefresh = false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAutoRefresh() {

    return this.autoRefresh;
  }

  /**
   * @param autoRefresh is the autoRefresh to set
   */
  public void setAutoRefresh(boolean autoRefresh) {

    this.autoRefresh = autoRefresh;
  }

}
