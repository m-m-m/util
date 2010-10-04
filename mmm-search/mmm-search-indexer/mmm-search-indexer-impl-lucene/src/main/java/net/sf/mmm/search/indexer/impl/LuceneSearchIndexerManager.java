/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexerManager;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexModifier;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class LuceneSearchIndexerManager extends AbstractSearchIndexerManager {

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /**
   * The constructor.
   */
  public LuceneSearchIndexerManager() {

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
  @Inject
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
  @Override
  protected SearchIndexer createIndexer(File indexPath, boolean update) throws SearchException {

    try {
      boolean create;
      if (indexPath.exists()) {
        if (indexPath.isDirectory()) {
          create = !update;
        } else {
          // TODO: i18n
          // dataSource
          throw new SearchException("Data source exists but is no directory \"{0}\"!",
              new HashMap<String, Object>());
        }
      } else {
        create = true;
      }
      IndexModifier modifier = new IndexModifier(indexPath, this.analyzer, create);
      return new LuceneSearchIndexer(modifier);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

}
