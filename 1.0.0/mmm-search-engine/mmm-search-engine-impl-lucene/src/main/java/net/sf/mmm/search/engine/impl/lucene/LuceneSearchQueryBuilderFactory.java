/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.search.engine.base.AbstractSearchQueryBuilderFactory;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzer;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzerImpl;
import net.sf.mmm.search.impl.lucene.LuceneVersion;
import net.sf.mmm.search.impl.lucene.LuceneVersionImpl;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.apache.lucene.analysis.Analyzer;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchQueryBuilderFactory} interface
 * using lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class LuceneSearchQueryBuilderFactory extends AbstractSearchQueryBuilderFactory {

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /** @see #setLuceneAnalyzer(LuceneAnalyzer) */
  private LuceneAnalyzer luceneAnalyzer;

  /** @see #setLuceneVersion(LuceneVersion) */
  private LuceneVersion luceneVersion;

  /** @see #setFieldManagerFactory(LuceneFieldManagerFactory) */
  private LuceneFieldManagerFactory fieldManagerFactory;

  /**
   * The constructor.
   */
  public LuceneSearchQueryBuilderFactory() {

    super();
  }

  /**
   * @return the {@link Analyzer} to use.
   */
  protected Analyzer getAnalyzer() {

    return this.analyzer;
  }

  /**
   * @param analyzer is the {@link Analyzer} to set.
   */
  public void setAnalyzer(Analyzer analyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = analyzer;
  }

  /**
   * @param luceneAnalyzer is the {@link LuceneAnalyzer} to inject.
   */
  @Inject
  public void setLuceneAnalyzer(LuceneAnalyzer luceneAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.luceneAnalyzer = luceneAnalyzer;
  }

  /**
   * @param luceneVersion is the luceneVersion to set
   */
  @Inject
  public void setLuceneVersion(LuceneVersion luceneVersion) {

    getInitializationState().requireNotInitilized();
    this.luceneVersion = luceneVersion;
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
    if (this.luceneVersion == null) {
      LuceneVersionImpl impl = new LuceneVersionImpl();
      impl.initialize();
      this.luceneVersion = impl;
    }
    if (this.analyzer == null) {
      if (this.luceneAnalyzer == null) {
        LuceneAnalyzerImpl impl = new LuceneAnalyzerImpl();
        impl.setLuceneVersion(this.luceneVersion);
        impl.initialize();
        this.luceneAnalyzer = impl;
      }
      this.analyzer = this.luceneAnalyzer.getAnalyzer();
    }
    if (this.fieldManagerFactory == null) {
      LuceneFieldManagerFactoryImpl impl = new LuceneFieldManagerFactoryImpl();
      impl.setAnalyzer(this.analyzer);
      impl.setLuceneVersion(this.luceneVersion);
      impl.initialize();
      this.fieldManagerFactory = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public LuceneSearchQueryBuilder createQueryBuilder(
      SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder) {

    NlsNullPointerException.checkNotNull(SearchConfigurationHolder.class, configurationHolder);
    LuceneFieldManager fieldManager = this.fieldManagerFactory
        .createFieldManager(configurationHolder);
    return new LuceneSearchQueryBuilder(this.analyzer, this.luceneVersion.getLuceneVersion(),
        fieldManager);
  }

}
