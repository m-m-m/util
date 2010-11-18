/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

/**
 * This is the interface for the {@link SearchFieldConfiguration
 * field-configurations}. It defines the meta-model (the schema) of a
 * {@link net.sf.mmm.search.api.SearchEntry}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchFields {

  /**
   * This method gets the {@link SearchFieldConfiguration} for the
   * {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) field} with the
   * given <code>name</code>. This method will always return a result for the
   * fields predefined by {@link net.sf.mmm.search.api.SearchEntry} (see
   * <code>FIELD_*</code> constants) even if they are NOT explicitly configured.
   * However for specific cases it is possible to override this defaults in your
   * configuration.
   * 
   * @param name is the {@link SearchFieldConfiguration#getName() name} of the
   *        requested {@link SearchFieldConfiguration}.
   * @return the requested {@link SearchFieldConfiguration} or <code>null</code>
   *         if no such
   *         {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) field} is
   *         defined or configured.
   */
  SearchFieldConfiguration getFieldConfiguration(String name);

  /**
   * This method is like {@link #getFieldConfiguration(String)} but creates a
   * default {@link SearchFieldConfiguration} if none is defined.
   * 
   * @param name is the {@link SearchFieldConfiguration#getName() name} of the
   *        requested {@link SearchFieldConfiguration}.
   * @return the requested {@link SearchFieldConfiguration}.
   */
  SearchFieldConfiguration getOrCreateFieldConfiguration(String name);

}
