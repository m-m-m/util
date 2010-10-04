/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.File;

import javax.inject.Inject;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerManager;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.file.api.FileUtil;

/**
 * This is the abstract base-implementation for the {@link SearchIndexerManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerManager extends AbstractLoggable implements
    SearchIndexerManager {

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public AbstractSearchIndexerManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexer createIndexer(SearchIndexConfiguration searchIndexConfiguration)
      throws SearchException {

    return createIndexer(searchIndexConfiguration.getLocation(),
        !searchIndexConfiguration.isOverwrite());
  }

  /**
   * {@inheritDoc}
   */
  public final SearchIndexer createIndexer(String dataSource, boolean update)
      throws SearchException {

    String indexDirectory = getFileUtil().normalizePath(dataSource);
    return createIndexer(new File(indexDirectory), update);
  }

  /**
   * This method is like {@link #createIndexer(String, boolean)} but for the
   * normalized index-location given as {@link File}.
   * 
   * @param indexPath is the {@link File} pointing to the directory for the
   *        index.
   * @param update if <code>true</code> the indexer will update the index at
   *        <code>indexPath</code> if it already exists, else if
   *        <code>false</code> the index will be overwritten if it already
   *        exists. If the index does NOT yet exist, it will be created in any
   *        case.
   * @return the create indexer.
   * @throws SearchException if the operation failed.
   */
  protected abstract SearchIndexer createIndexer(File indexPath, boolean update)
      throws SearchException;

  /**
   * @return the fileUtil
   */
  protected FileUtil getFileUtil() {

    return this.fileUtil;
  }

  /**
   * @param fileUtil is the fileUtil to set
   */
  @Inject
  public void setFileUtil(FileUtil fileUtil) {

    getInitializationState().requireNotInitilized();
    this.fileUtil = fileUtil;
  }

}
