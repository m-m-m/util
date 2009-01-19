/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexModifier;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchIoException;
import net.sf.mmm.search.indexer.api.SearchIndexManager;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexManager extends AbstractLoggable implements SearchIndexManager {

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /**
   * The constructor.
   */
  public LuceneSearchIndexManager() {

    super();
    this.analyzer = null;
  }

  /**
   * @return the analyzer
   */
  protected Analyzer getAnalyzer() {

    return this.analyzer;
  }

  /**
   * @param newAnalyzer the analyzer to set
   */
  @Resource()
  public void setAnalyzer(Analyzer newAnalyzer) {

    this.analyzer = newAnalyzer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.analyzer == null) {
      this.analyzer = new StandardAnalyzer();
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexer createIndexer(String dataSource, boolean update) throws SearchException {

    File indexPath = new File(dataSource);
    try {
      boolean create;
      if (indexPath.exists()) {
        if (indexPath.isDirectory()) {
          create = !update;
        } else {
          // TODO: i18n
          throw new SearchException("Data source exists but is no directory \"{0}\"!", dataSource);
        }
      } else {
        create = true;
      }
      IndexModifier modifier = new IndexModifier(indexPath, this.analyzer, create);
      return new LuceneSearchIndexer(modifier);
    } catch (IOException e) {
      throw new SearchIoException(e);
    }
  }

}
