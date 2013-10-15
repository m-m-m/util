/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.io.IOException;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.base.SearchEntryIdInvalidException;
import net.sf.mmm.search.base.SearchEntryIdMissingException;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.base.AbstractSearchEngine;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.engine.base.SearchHitImpl;
import net.sf.mmm.search.engine.base.SearchResultPageImpl;
import net.sf.mmm.search.impl.lucene.LuceneSearchEntry;
import net.sf.mmm.util.component.api.PeriodicRefresher;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;

/**
 * This is the implementation of the {@link net.sf.mmm.search.engine.api.SearchEngine} interface using lucene
 * as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEngine extends AbstractSearchEngine {

  /** The lucene {@link Analyzer}. */
  private final Analyzer analyzer;

  /** @see #getQueryBuilder() */
  private final SearchQueryBuilder queryBuilder;

  /** @see #getHighlightFormatter() */
  private final Formatter highlightFormatter;

  /** @see #getFieldManager() */
  private final LuceneFieldManager fieldManager;

  /** @see #getSearchDependencies() */
  private final SearchDependencies searchDependencies;

  /** The {@link IndexReader} used for the {@link #searcher}. */
  private IndexReader indexReader;

  /** The actual lucene {@link Searcher}. */
  private Searcher searcher;

  /**
   * The constructor.
   * 
   * @param indexReader is the {@link IndexReader} to access the index.
   * @param analyzer is the {@link Analyzer} to use.
   * @param queryBuilder is the {@link SearchQueryBuilder} query builder.
   * @param highlightFormatter is the {@link Formatter} for highlighting.
   * @param fieldManager is the {@link LuceneFieldManager}.
   * @param searchDependencies are the {@link SearchDependencies}.
   * @param periodicRefresher is the {@link PeriodicRefresher}.
   */
  public LuceneSearchEngine(IndexReader indexReader, Analyzer analyzer, SearchQueryBuilder queryBuilder,
      Formatter highlightFormatter, LuceneFieldManager fieldManager, SearchDependencies searchDependencies,
      PeriodicRefresher periodicRefresher) {

    super(periodicRefresher);
    NlsNullPointerException.checkNotNull(IndexReader.class, indexReader);
    NlsNullPointerException.checkNotNull(Analyzer.class, analyzer);
    NlsNullPointerException.checkNotNull(SearchQueryBuilder.class, queryBuilder);
    NlsNullPointerException.checkNotNull(Formatter.class, highlightFormatter);
    NlsNullPointerException.checkNotNull(LuceneFieldManager.class, fieldManager);
    NlsNullPointerException.checkNotNull(SearchDependencies.class, searchDependencies);
    // searchEngineRefresher is allowed to be null
    this.indexReader = indexReader;
    this.fieldManager = fieldManager;
    this.searchDependencies = searchDependencies;
    this.searcher = new IndexSearcher(this.indexReader);
    this.analyzer = analyzer;
    this.queryBuilder = queryBuilder;
    this.highlightFormatter = highlightFormatter;
  }

  /**
   * The constructor.
   * 
   * @param indexReader is the {@link IndexReader}.
   * @param analyzer is the {@link Analyzer} to use.
   * @param queryBuilder is the {@link SearchQueryBuilder} query builder.
   * @param highlightFormatter is the {@link Formatter} for highlighting.
   * @param fieldManager is the {@link LuceneFieldManager}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public LuceneSearchEngine(IndexReader indexReader, Analyzer analyzer, SearchQueryBuilder queryBuilder,
      Formatter highlightFormatter, LuceneFieldManager fieldManager, SearchDependencies searchDependencies) {

    this(indexReader, analyzer, queryBuilder, highlightFormatter, fieldManager, searchDependencies, null);
  }

  /**
   * This method gets the {@link SearchDependencies}.
   * 
   * @return the {@link SearchDependencies}.
   */
  protected SearchDependencies getSearchDependencies() {

    return this.searchDependencies;
  }

  /**
   * This method gets the {@link LuceneFieldManager}.
   * 
   * @return the {@link LuceneFieldManager}.
   */
  protected LuceneFieldManager getFieldManager() {

    return this.fieldManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchQueryBuilder getQueryBuilder() {

    return this.queryBuilder;
  }

  /**
   * @return the formatter
   */
  protected Formatter getHighlightFormatter() {

    return this.highlightFormatter;
  }

  /**
   * This method gets the lucene {@link Query} for the given <code>query</code>.
   * 
   * @param query is the {@link SearchQuery} to "convert".
   * @return the lucene {@link Query}.
   */
  protected Query getLuceneQuery(SearchQuery query) {

    try {
      Query luceneQuery = ((AbstractLuceneSearchQuery) query).getLuceneQuery();
      luceneQuery = this.searcher.rewrite(luceneQuery);
      return luceneQuery;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * This method creates a new instance of the {@link SearchHighlighter} for a {@link SearchResultPage}.
   * 
   * @param luceneQuery is the lucene {@link Query} used for highlighting.
   * @return the {@link SearchHighlighter}.
   */
  protected SearchHighlighter createSearchHighlighter(Query luceneQuery) {

    return new LuceneSearchHighlighter(this.analyzer, this.highlightFormatter, luceneQuery);
  }

  /**
   * This method creates an {@link SearchHit} for the given <code>documentId</code> and <code>score</code>.
   * 
   * @param documentId is the technical ID of the lucene {@link Document} representing the hit.
   * @param score is the {@link SearchHit#getScore() score} of the hit.
   * @param searchHighlighter is used to create the {@link SearchHit#getHighlightedText() highlighted text}.
   * @return the {@link SearchHit}.
   */
  protected SearchHit createSearchHit(int documentId, double score, SearchHighlighter searchHighlighter) {

    SearchEntry searchEntry = getEntry(documentId);
    return new SearchHitImpl(searchEntry, Integer.toString(documentId), score, searchHighlighter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResultPage search(SearchQuery query, int hitsPerPage) {

    return search(query, hitsPerPage, 0, -1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResultPage search(SearchQuery query, int hitsPerPage, int pageIndex, int totalHitCount) {

    try {
      Query luceneQuery = getLuceneQuery(query);
      int start = hitsPerPage * pageIndex;
      int requiredHitCount = hitsPerPage + start;
      // do the actual search...
      TopDocs topDocs = this.searcher.search(luceneQuery, requiredHitCount);
      int pageHitCount = topDocs.scoreDocs.length - start;
      float maxScore = topDocs.getMaxScore();
      if (Float.isNaN(maxScore) || (maxScore <= 0)) {
        maxScore = 1;
      }
      SearchHit[] hits;
      if (pageHitCount > 0) {
        hits = new SearchHit[pageHitCount];
        SearchHighlighter searchHighlighter = createSearchHighlighter(luceneQuery);
        for (int i = 0; i < hits.length; i++) {
          ScoreDoc scoreDoc = topDocs.scoreDocs[start + i];
          hits[i] = createSearchHit(scoreDoc.doc, (scoreDoc.score / maxScore), searchHighlighter);
        }
      } else {
        hits = SearchHit.NO_HITS;
      }
      SearchResultPage page = new SearchResultPageImpl(query.toString(), topDocs.totalHits, hitsPerPage, pageIndex,
          hits);
      return page;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long count(String field, String value) {

    try {
      Term term = this.fieldManager.createTerm(field, value);
      return this.searcher.docFreq(term);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getTotalEntryCount() {

    try {
      return this.searcher.maxDoc();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchEntry getEntry(String entryId) {

    try {
      return getEntry(Integer.parseInt(entryId));
    } catch (NumberFormatException e) {
      throw new SearchEntryIdInvalidException(e, entryId);
    }
  }

  /**
   * @see #getEntry(String)
   * 
   * @param documentId is the technical ID of the lucene {@link Document} representing the hit.
   * @return the document as {@link SearchEntry}.
   */
  public SearchEntry getEntry(int documentId) {

    try {
      Document doc = this.searcher.doc(documentId);
      if (doc == null) {
        throw new SearchEntryIdMissingException(Integer.toString(documentId));
      }
      return new LuceneSearchEntry(doc, this.fieldManager.getConfigurationHolder().getBean().getFields(),
          this.searchDependencies);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean refresh() {

    try {
      boolean updated = this.fieldManager.refresh();
      IndexReader oldReader = this.indexReader;
      this.indexReader = this.indexReader.reopen();
      if (this.indexReader != oldReader) {
        getLogger().debug("search-index has changed and search-engine is refreshed!");
        Searcher oldSearcher = this.searcher;
        this.searcher = new IndexSearcher(this.indexReader);
        try {
          oldSearcher.close();
        } finally {
          oldReader.close();
        }
        updated = true;
      }
      this.queryBuilder.refresh();
      return updated;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {

    super.close();
    try {
      this.searcher.close();
      this.searcher = null;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
