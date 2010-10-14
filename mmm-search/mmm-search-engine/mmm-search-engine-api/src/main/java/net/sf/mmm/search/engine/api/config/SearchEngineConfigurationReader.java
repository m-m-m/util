/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

/**
 * This is the interface for {@link #readConfiguration(String) reading} the
 * {@link SearchEngineConfiguration} for a given data location. The
 * implementation shall at least support reading the configuration from the
 * filesystem as XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineConfigurationReader {

  /**
   * This method reads the configuration from the given <code>location</code>.
   * The configuration data is parsed according to its format (typically XML)
   * and returned as {@link SearchEngineConfiguration}.
   * 
   * @param location is the location where the configuration data shall be read
   *        from. This is typically a path into the filesystem.
   * @return the parsed {@link SearchEngineConfiguration}.
   */
  SearchEngineConfiguration readConfiguration(String location);

}
