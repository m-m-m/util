/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.api.config.SearchProperties;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.base.AbstractSearchEngineBuilder;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzer;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzerImpl;
import net.sf.mmm.search.impl.lucene.LuceneDirectoryBuilder;
import net.sf.mmm.search.impl.lucene.LuceneDirectoryBuilderImpl;
import net.sf.mmm.util.component.api.PeriodicRefresher;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.store.Directory;

/**
 * This is the implementation of {@link SearchEngineBuilder} using apache lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(SearchEngineBuilder.CDI_NAME)
public class LuceneSearchEngineBuilder extends AbstractSearchEngineBuilder {

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /** @see #setLuceneAnalyzer(LuceneAnalyzer) */
  private LuceneAnalyzer luceneAnalyzer;

  /** @see #getLuceneDirectoryBuilder() */
  private LuceneDirectoryBuilder luceneDirectoryBuilder;

  /** @see #getHighlightFormatter() */
  private Formatter highlightFormatter;

  /** @see #getFieldManagerFactory() */
  private LuceneFieldManagerFactory fieldManagerFactory;

  /**
   * The constructor.
   */
  public LuceneSearchEngineBuilder() {

    super();
  }

  /**
   * @param analyzer is the analyzer to set
   */
  public void setAnalyzer(Analyzer analyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = analyzer;
  }

  /**
   * @return the analyzer
   */
  protected Analyzer getAnalyzer() {

    return this.analyzer;
  }

  /**
   * This method sets (injects) the {@link LuceneAnalyzer}. You may also use {@link #setAnalyzer(Analyzer)} if
   * you assemble this component manually.
   * 
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
   * @return the luceneDirectoryBuilder
   */
  protected LuceneDirectoryBuilder getLuceneDirectoryBuilder() {

    return this.luceneDirectoryBuilder;
  }

  /**
   * @return the highlightFormatter
   */
  protected Formatter getHighlightFormatter() {

    return this.highlightFormatter;
  }

  /**
   * @param highlightFormatter is the highlightFormatter to set
   */
  @Inject
  public void setHighlightFormatter(Formatter highlightFormatter) {

    getInitializationState().requireNotInitilized();
    this.highlightFormatter = highlightFormatter;
  }

  /**
   * This method gets the {@link LuceneFieldManagerFactory}.
   * 
   * @return the {@link LuceneFieldManagerFactory}.
   */
  protected LuceneFieldManagerFactory getFieldManagerFactory() {

    return this.fieldManagerFactory;
  }

  /**
   * @param fieldManagerFactory is the fieldManagerFactory to set
   */
  @Inject
  public void setFieldManagerFactory(LuceneFieldManagerFactory fieldManagerFactory) {

    getInitializationState().requireNotInitilized();
    this.fieldManagerFactory = fieldManagerFactory;
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
    if (getSearchQueryBuilderFactory() == null) {
      LuceneSearchQueryBuilderFactory factory = new LuceneSearchQueryBuilderFactory();
      factory.setLuceneAnalyzer(this.luceneAnalyzer);
      factory.setAnalyzer(this.analyzer);
      factory.initialize();
      setSearchQueryBuilderFactory(factory);
    }
    if (this.highlightFormatter == null) {
      this.highlightFormatter = new HighlightFormatter();
    }
    if (this.luceneDirectoryBuilder == null) {
      LuceneDirectoryBuilderImpl directoryBuilderImpl = new LuceneDirectoryBuilderImpl();
      directoryBuilderImpl.initialize();
      this.luceneDirectoryBuilder = directoryBuilderImpl;
    }
    if (this.fieldManagerFactory == null) {
      LuceneFieldManagerFactoryImpl impl = new LuceneFieldManagerFactoryImpl();
      impl.setAnalyzer(this.analyzer);
      impl.initialize();
      this.fieldManagerFactory = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ManagedSearchEngine createSearchEngine(SearchEngineConfigurationHolder configurationHolder) {

    NlsNullPointerException.checkNotNull(SearchEngineConfigurationHolder.class, configurationHolder);
    SearchEngineConfiguration configuration = configurationHolder.getBean();
    SearchProperties properties = configuration.getProperties();
    NlsNullPointerException.checkNotNull(SearchProperties.class, properties);
    SearchIndexConfiguration indexConfiguration = configuration.getSearchIndex();
    NlsNullPointerException.checkNotNull(SearchIndexConfiguration.class, indexConfiguration);
    try {
      Directory directory = this.luceneDirectoryBuilder.createDirectory(indexConfiguration);
      PeriodicRefresher periodicRefresher = null;
      String autoRefresh = properties.getProperty(SearchProperties.KEY_AUTO_REFRESH);
      if (StringUtil.TRUE.equals(autoRefresh)) {
        periodicRefresher = getPeriodicRefresher();
      }
      IndexReader indexReader = IndexReader.open(directory, true);
      return createSearchEngine(indexReader, configurationHolder, periodicRefresher);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * This method creates a {@link ManagedSearchEngine} for an existing {@link IndexReader}.
   * 
   * @param indexReader is the {@link IndexReader}.
   * @param configurationHolder is the {@link SearchConfigurationHolder}.
   * @param refresher is the {@link PeriodicRefresher} or <code>null</code> to disable auto-refresh.
   * @return the {@link ManagedSearchEngine}.
   */
  public ManagedSearchEngine createSearchEngine(IndexReader indexReader,
      SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder, PeriodicRefresher refresher) {

    LuceneFieldManager fieldManager = this.fieldManagerFactory.createFieldManager(configurationHolder);
    LuceneSearchEngine engine = new LuceneSearchEngine(indexReader, this.analyzer, getSearchQueryBuilderFactory()
        .createQueryBuilder(configurationHolder), this.highlightFormatter, fieldManager, getSearchDependencies(),
        refresher);
    engine.initialize();
    return engine;
  }

}
