/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;

import javax.inject.Inject;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchEntryIdInvalidException;
import net.sf.mmm.search.base.SearchEntryIdMissingException;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResult;
import net.sf.mmm.search.engine.base.AbstractSearchEngine;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.impl.LuceneSearchEntry;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.highlight.Formatter;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchEngine} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEngine extends AbstractSearchEngine {

  /** @see #getQueryBuilder() */
  private SearchQueryBuilder queryBuilder;

  /** @see #setAnalyzer(Analyzer) */
  private Analyzer analyzer;

  /** the lucene query parser */
  private QueryParser queryParser;

  /** @see #setSearcher(Searcher) */
  private Searcher searcher;

  /** @see #setIndexPath(String) */
  private String indexPath;

  /** @see #getHighlightFormatter() */
  private Formatter highlightFormatter;

  /**
   * The constructor.
   */
  public LuceneSearchEngine() {

    super();
  }

  /**
   * This method sets the lucene analyzer used by this search engine.
   * 
   * @param luceneAnalyzer the analyzer to set
   */
  @Inject
  public void setAnalyzer(Analyzer luceneAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = luceneAnalyzer;
  }

  /**
   * This method sets the lucene searcher used by this search engine.
   * 
   * @param luceneSearcher the searcher to set
   */
  public void setSearcher(Searcher luceneSearcher) {

    getInitializationState().requireNotInitilized();
    this.searcher = luceneSearcher;
  }

  /**
   * This method sets the path in the local filesystem where the search-index is
   * located. You can also use {@link #setSearcher(Searcher)} instead.
   * 
   * @param searchIndexPath the indexPath to set
   */
  public void setIndexPath(String searchIndexPath) {

    getInitializationState().requireNotInitilized();
    this.indexPath = searchIndexPath;
  }

  /**
   * {@inheritDoc}
   */
  public SearchQueryBuilder getQueryBuilder() {

    return this.queryBuilder;
  }

  /**
   * This method sets the {@link #getQueryBuilder() query-builder}.
   * 
   * @param searchQueryBuilder the queryBuilder to set
   */
  public void setQueryBuilder(SearchQueryBuilder searchQueryBuilder) {

    getInitializationState().requireNotInitilized();
    this.queryBuilder = searchQueryBuilder;
  }

  /**
   * @return the formatter
   */
  protected Formatter getHighlightFormatter() {

    return this.highlightFormatter;
  }

  /**
   * @param formatter the formatter to set
   */
  @Inject
  public void setHighlightFormatter(Formatter formatter) {

    getInitializationState().requireNotInitilized();
    this.highlightFormatter = formatter;
  }

  /**
   * This method has to be called before the search-engine can be used. You have
   * to inject the {@link #setIndexPath(String) index-path} or the
   * {@link #setSearcher(Searcher) searcher} before you can call this method.
   * 
   * {@inheritDoc}
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
    try {
      if (this.analyzer == null) {
        this.analyzer = new StandardAnalyzer();
      }
      if (this.queryParser == null) {
        this.queryParser = new QueryParser(SearchEntry.PROPERTY_TEXT, this.analyzer);
      }
      if (this.searcher == null) {
        if (this.indexPath == null) {
          throw new ResourceMissingException("indexPath");
        }
        this.searcher = new IndexSearcher(this.indexPath);
      }
      if (this.queryBuilder == null) {
        LuceneSearchQueryBuilder qb = new LuceneSearchQueryBuilder();
        qb.setAnalyzer(this.analyzer);
        this.queryBuilder = qb;
      }
      if (this.highlightFormatter == null) {
        this.highlightFormatter = new HighlightFormatter();
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchResult search(SearchQuery query) {

    try {
      Query luceneQuery = ((AbstractLuceneSearchQuery) query).getLuceneQuery();
      luceneQuery = this.searcher.rewrite(luceneQuery);
      Hits hits = this.searcher.search(luceneQuery);
      SearchHighlighter highlighter;
      if (hits.length() > 0) {
        highlighter = new LuceneSearchHighlighter(this.analyzer, this.highlightFormatter,
            luceneQuery);
      } else {
        highlighter = null;
      }
      return new LuceneSearchResult(query.toString(), hits, highlighter);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchEntry getEntry(String id) throws SearchException {

    try {
      int docId = Integer.valueOf(id).intValue();
      Document doc = this.searcher.doc(docId);
      if (doc == null) {
        throw new SearchEntryIdMissingException(id);
      }
      return new LuceneSearchEntry(doc);
    } catch (NumberFormatException e) {
      throw new SearchEntryIdInvalidException(e, id);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void refresh() {

    try {
      this.searcher.close();
      // TODO: do we need to do this in finally? What happened to the searcher
      // if close caused an I/O error?
      this.searcher = new IndexSearcher(this.indexPath);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    try {
      this.searcher.close();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
