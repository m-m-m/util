/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a factory used to create a {@link SearchQueryBuilder}.<br/>
 * End users should typically use {@link SearchEngine#getQueryBuilder()} instead.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchQueryBuilderFactory {

  // /**
  // * This method will create a default {@link SearchQueryBuilder}.
  // *
  // * @return the new {@link SearchQueryBuilder}.
  // */
  // SearchQueryBuilder createQueryBuilder();

  /**
   * This method will create a new {@link SearchQueryBuilder}.
   * 
   * @param configurationHolder is the {@link SearchConfigurationHolder}. The
   *        {@link net.sf.mmm.search.api.config.SearchConfiguration#getFields() fields} will be used for the
   *        query parsing and {@link net.sf.mmm.search.api.config.SearchConfiguration#getProperties()
   *        properties} may be used for implementation specific features.
   * @return the new {@link SearchQueryBuilder}.
   */
  SearchQueryBuilder createQueryBuilder(SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder);

}
