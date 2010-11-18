/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

/**
 * This is the interface for the additional properties to use for the
 * {@link net.sf.mmm.search.engine.api.SearchEngine} or indexer.
 * 
 * @see net.sf.mmm.search.base.config.SearchPropertiesBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchProperties {

  /**
   * The key for the auto-refresh {@link #getProperty(String) option}. A value
   * of "true" indicates that the
   * {@link net.sf.mmm.search.engine.api.SearchEngine} should be
   * {@link net.sf.mmm.search.engine.api.ManagedSearchEngine#refresh()
   * refreshed} automatically within a periodically delay.
   */
  String KEY_AUTO_REFRESH = "auto-refresh";

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
