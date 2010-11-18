/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.base.SearchDependenciesImpl;
import net.sf.mmm.search.engine.impl.lucene.LuceneFieldManager;
import net.sf.mmm.search.engine.impl.lucene.LuceneFieldManagerFactory;
import net.sf.mmm.search.engine.impl.lucene.LuceneFieldManagerFactoryImpl;
import net.sf.mmm.search.engine.impl.lucene.LuceneSearchEngineBuilder;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzer;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzerImpl;
import net.sf.mmm.search.impl.lucene.LuceneDirectoryBuilder;
import net.sf.mmm.search.impl.lucene.LuceneDirectoryBuilderImpl;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.search.indexer.api.config.SearchIndexerOptions;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexerBuilder;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class LuceneSearchIndexerBuilder extends AbstractSearchIndexerBuilder {

  /** @see #setLuceneAnalyzer(LuceneAnalyzer) */
  private LuceneAnalyzer luceneAnalyzer;

  /** @see #setLuceneDirectoryBuilder(LuceneDirectoryBuilder) */
  private LuceneDirectoryBuilder luceneDirectoryBuilder;

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /** @see #setLuceneSearchEngineBuilder(LuceneSearchEngineBuilder) */
  private LuceneSearchEngineBuilder luceneSearchEngineBuilder;

  /** @see #setFiedManagerFactory(LuceneFieldManagerFactory) */
  private LuceneFieldManagerFactory fiedManagerFactory;

  /** @see #setSearchDependencies(SearchDependencies) */
  private SearchDependencies searchDependencies;

  /**
   * The constructor.
   */
  public LuceneSearchIndexerBuilder() {

    super();
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
  public void setAnalyzer(Analyzer newAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = newAnalyzer;
  }

  /**
   * @param luceneAnalyzer is the luceneAnalyzer to set
   */
  @Inject
  public void setLuceneAnalyzer(LuceneAnalyzer luceneAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.luceneAnalyzer = luceneAnalyzer;
  }

  /**
   * @param luceneDirectoryBuilder is the luceneDirectoryBuilder to set
   */
  @Inject
  public void setLuceneDirectoryBuilder(LuceneDirectoryBuilder luceneDirectoryBuilder) {

    getInitializationState().requireNotInitilized();
    this.luceneDirectoryBuilder = luceneDirectoryBuilder;
  }

  /**
   * @param luceneSearchEngineBuilder is the luceneSearchEngineBuilder to set
   */
  @Inject
  public void setLuceneSearchEngineBuilder(LuceneSearchEngineBuilder luceneSearchEngineBuilder) {

    getInitializationState().requireNotInitilized();
    this.luceneSearchEngineBuilder = luceneSearchEngineBuilder;
  }

  /**
   * @param fiedManagerFactory is the fiedManagerFactory to set
   */
  @Inject
  public void setFiedManagerFactory(LuceneFieldManagerFactory fiedManagerFactory) {

    getInitializationState().requireNotInitilized();
    this.fiedManagerFactory = fiedManagerFactory;
  }

  /**
   * @param searchDependencies is the searchDependencies to set
   */
  @Inject
  public void setSearchDependencies(SearchDependencies searchDependencies) {

    getInitializationState().requireNotInitilized();
    this.searchDependencies = searchDependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.analyzer == null) {
      if (this.luceneAnalyzer == null) {
        LuceneAnalyzerImpl luceneAnalyzerImpl = new LuceneAnalyzerImpl();
        luceneAnalyzerImpl.initialize();
        this.luceneAnalyzer = luceneAnalyzerImpl;
      }
      this.analyzer = this.luceneAnalyzer.getAnalyzer();
    }
    if (this.luceneDirectoryBuilder == null) {
      LuceneDirectoryBuilderImpl luceneDirectoryBuilderImpl = new LuceneDirectoryBuilderImpl();
      luceneDirectoryBuilderImpl.initialize();
      this.luceneDirectoryBuilder = luceneDirectoryBuilderImpl;
    }
    if (this.luceneSearchEngineBuilder == null) {
      LuceneSearchEngineBuilder searchEngineBuilder = new LuceneSearchEngineBuilder();
      searchEngineBuilder.setAnalyzer(this.analyzer);
      searchEngineBuilder.initialize();
      this.luceneSearchEngineBuilder = searchEngineBuilder;
    }
    if (this.fiedManagerFactory == null) {
      LuceneFieldManagerFactoryImpl impl = new LuceneFieldManagerFactoryImpl();
      impl.setAnalyzer(this.analyzer);
      impl.initialize();
      this.fiedManagerFactory = impl;
    }
    if (this.searchDependencies == null) {
      SearchDependenciesImpl impl = new SearchDependenciesImpl();
      impl.initialize();
      this.searchDependencies = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexer createIndexer(SearchIndexerConfigurationHolder configurationHolder,
      SearchIndexerOptions options) throws SearchException {

    NlsNullPointerException.checkNotNull(SearchIndexerConfigurationHolder.class,
        configurationHolder);
    NlsNullPointerException.checkNotNull(SearchIndexerOptions.class, options);
    SearchIndexerConfiguration configuration = configurationHolder.getBean();
    NlsNullPointerException.checkNotNull(SearchIndexerConfiguration.class, configuration);
    try {
      Directory directory = this.luceneDirectoryBuilder.createDirectory(configuration
          .getSearchIndex());
      IndexWriter indexWriter;
      MaxFieldLength maxFieldLength = MaxFieldLength.UNLIMITED;
      if (options.isOverwriteIndex()) {
        indexWriter = new IndexWriter(directory, this.analyzer, true, maxFieldLength);
      } else {
        indexWriter = new IndexWriter(directory, this.analyzer, maxFieldLength);
      }
      LuceneFieldManager fieldManager = this.fiedManagerFactory
          .createFieldManager(configurationHolder);
      return new LuceneSearchIndexer(indexWriter, this.luceneSearchEngineBuilder, fieldManager,
          this.searchDependencies);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }
}
