/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * This is the implementation of the {@link LuceneAnalyzer} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class LuceneAnalyzerImpl extends AbstractComponent implements LuceneAnalyzer {

  /** @see #setLuceneVersion(LuceneVersion) */
  private LuceneVersion luceneVersion;

  /** @see #getAnalyzer() */
  private Analyzer analyzer;

  /**
   * The constructor.
   */
  public LuceneAnalyzerImpl() {

    super();
    this.luceneVersion = null;
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
      this.analyzer = new StandardAnalyzer(this.luceneVersion.getLuceneVersion());
    }
  }

  /**
   * {@inheritDoc}
   */
  public Analyzer getAnalyzer() {

    getInitializationState().requireInitilized();
    return this.analyzer;
  }

  /**
   * @param analyzer is the analyzer to set
   */
  public void setAnalyzer(Analyzer analyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = analyzer;
  }

  /**
   * @param luceneVersion is the luceneVersion to set
   */
  @Inject
  public void setLuceneVersion(LuceneVersion luceneVersion) {

    getInitializationState().requireNotInitilized();
    this.luceneVersion = luceneVersion;
  }

}
