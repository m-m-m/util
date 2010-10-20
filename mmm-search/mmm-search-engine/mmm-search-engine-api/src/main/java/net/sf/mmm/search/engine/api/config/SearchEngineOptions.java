/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

/**
 * This is the interface for the additional options to use for the
 * {@link net.sf.mmm.search.engine.api.SearchEngine}.
 * 
 * @see net.sf.mmm.search.engine.api.SearchEngineBuilder#createSearchEngine(net.sf.mmm.search.api.config.SearchIndexConfiguration,
 *      SearchEngineOptions)
 * @see net.sf.mmm.search.engine.base.config.SearchEngineOptionsBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineOptions {

  /**
   * This method determines if the
   * {@link net.sf.mmm.search.engine.api.SearchEngine} should be
   * {@link net.sf.mmm.search.engine.api.ManagedSearchEngine#refresh()
   * refreshed} automatically within a periodically delay.<br/>
   * Auto-refresh is NOT enabled by default and has to be turned on explicitly.
   * 
   * @return <code>true</code> if auto-refresh should be enabled,
   *         <code>false</code> otherwise.
   */
  boolean isAutoRefresh();

}
