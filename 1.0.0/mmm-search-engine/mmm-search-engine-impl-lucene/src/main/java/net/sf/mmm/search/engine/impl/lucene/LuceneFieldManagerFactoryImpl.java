/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzer;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzerImpl;
import net.sf.mmm.search.impl.lucene.LuceneVersion;
import net.sf.mmm.search.impl.lucene.LuceneVersionImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;

import org.apache.lucene.analysis.Analyzer;

/**
 * This is the factory used to
 * {@link #createFieldManager(SearchConfigurationHolder) create}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class LuceneFieldManagerFactoryImpl extends AbstractLoggableComponent implements
    LuceneFieldManagerFactory {

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /** @see #setLuceneAnalyzer(LuceneAnalyzer) */
  private LuceneAnalyzer luceneAnalyzer;

  /** @see #setLuceneVersion(LuceneVersion) */
  private LuceneVersion luceneVersion;

  /** @see #setIso8601Util(Iso8601Util) */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public LuceneFieldManagerFactoryImpl() {

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
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.luceneVersion == null) {
      LuceneVersionImpl luceneVersionImpl = new LuceneVersionImpl();
      luceneVersionImpl.initialize();
      this.luceneVersion = luceneVersionImpl;
    }
    if (this.analyzer == null) {
      if (this.luceneAnalyzer == null) {
        LuceneAnalyzerImpl luceneAnalyzerImpl = new LuceneAnalyzerImpl();
        luceneAnalyzerImpl.setLuceneVersion(this.luceneVersion);
        luceneAnalyzerImpl.initialize();
        this.luceneAnalyzer = luceneAnalyzerImpl;
      }
      this.analyzer = this.luceneAnalyzer.getAnalyzer();
    }
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public LuceneFieldManager createFieldManager(
      SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder) {

    return new LuceneFieldManagerImpl(configurationHolder, this.analyzer, this.iso8601Util);
  }
}
