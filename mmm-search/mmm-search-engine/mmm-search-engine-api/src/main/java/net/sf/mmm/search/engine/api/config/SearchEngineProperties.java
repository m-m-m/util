/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

/**
 * This is the interface for the additional options to use for the
 * {@link net.sf.mmm.search.engine.api.SearchEngine}.
 * 
 * @see net.sf.mmm.search.engine.api.SearchEngineBuilder#createSearchEngine(net.sf.mmm.search.api.config.SearchIndexConfiguration,
 *      SearchEngineProperties)
 * @see net.sf.mmm.search.engine.base.config.SearchEnginePropertiesBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineProperties {

  /**
   * The key for the auto-refresh {@link #getProperty(String) option}. A value
   * of "true" indicates that the
   * {@link net.sf.mmm.search.engine.api.SearchEngine} should be
   * {@link net.sf.mmm.search.engine.api.ManagedSearchEngine#refresh()
   * refreshed} automatically within a periodically delay. If this option is NOT
   * set (<code>null</code>), a value of "true" will be assumed. Set to "false"
   * to disable.
   */
  String KEY_AUTO_REFRESH = "auto-refresh";

  // /**
  // * This method determines if the
  // * {@link net.sf.mmm.search.engine.api.SearchEngine} should be
  // * {@link net.sf.mmm.search.engine.api.ManagedSearchEngine#refresh()
  // * refreshed} automatically within a periodically delay.<br/>
  // * Auto-refresh is NOT enabled by default and has to be turned on
  // explicitly.
  // *
  // * @return <code>true</code> if auto-refresh should be enabled,
  // * <code>false</code> otherwise.
  // */
  // boolean isAutoRefresh();

  /**
   * This method gets a generic property that may influence the
   * {@link net.sf.mmm.search.engine.api.SearchEngine}. The properties may be
   * implementation-dependent. Undefined/unsupported properties will simply be
   * ignored.
   * 
   * @see #KEY_AUTO_REFRESH
   * @see java.util.Map#get(Object)
   * @see java.util.Properties#getProperty(String)
   * 
   * @param key is the key of the requested configuration property.
   * @return the value of the property for the given <code>key</code> or
   *         <code>null</code> if the property is undefined.
   */
  String getProperty(String key);

}
