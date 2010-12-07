/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a factory used to
 * {@link #createFieldManager(SearchConfigurationHolder) create} a
 * {@link LuceneFieldManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface LuceneFieldManagerFactory {

  /**
   * This method creates a new {@link LuceneFieldManager} instance for the given
   * <code>configuration</code>.
   * 
   * @param configurationHolder is the {@link SearchConfigurationHolder}.
   * @return the {@link LuceneFieldManager}.
   */
  LuceneFieldManager createFieldManager(
      SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder);

}
