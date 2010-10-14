/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.config.SearchIndexConfigurationBean;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerBuilder;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base-implementation for the {@link SearchIndexerBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerBuilder extends AbstractLoggable implements
    SearchIndexerBuilder {

  /**
   * The constructor.
   */
  public AbstractSearchIndexerBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final SearchIndexer createIndexer(String dataSource, boolean overwrite)
      throws SearchException {

    SearchIndexConfigurationBean configuration = new SearchIndexConfigurationBean();
    configuration.setLocation(dataSource);
    configuration.setOverwrite(overwrite);
    return createIndexer(configuration);
  }

  // /**
  // * This method is like {@link #createIndexer(String, boolean)} but for the
  // * normalized index-location given as {@link File}.
  // *
  // * @param indexPath is the {@link File} pointing to the directory for the
  // * index.
  // * @param update if <code>true</code> the indexer will update the index at
  // * <code>indexPath</code> if it already exists, else if
  // * <code>false</code> the index will be overwritten if it already
  // * exists. If the index does NOT yet exist, it will be created in any
  // * case.
  // * @return the create indexer.
  // * @throws SearchException if the operation failed.
  // */
  // protected abstract SearchIndexer createIndexer(File indexPath, boolean
  // update)
  // throws SearchException;

}
