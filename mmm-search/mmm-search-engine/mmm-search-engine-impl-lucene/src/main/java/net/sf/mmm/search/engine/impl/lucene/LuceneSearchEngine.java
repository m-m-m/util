/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.io.IOException;
import java.util.Date;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchEntryIdInvalidException;
import net.sf.mmm.search.base.SearchEntryIdMissingException;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.base.AbstractSearchEngine;
import net.sf.mmm.search.engine.base.SearchEngineRefresher;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.engine.base.SearchHitImpl;
import net.sf.mmm.search.engine.base.SearchResultPageImpl;
import net.sf.mmm.search.impl.lucene.LuceneSearchEntry;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.store.Directory;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchEngine} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEngine extends AbstractSearchEngine {

  /** The lucene index directory. */
  private final Directory directory;

  /** The lucene {@link Analyzer}. */
  private final Analyzer analyzer;

  /** @see #getQueryBuilder() */
  private final SearchQueryBuilder queryBuilder;

  /** @see #getHighlightFormatter() */
  private final Formatter highlightFormatter;

  /** The actual lucene {@link Searcher}. */
  private Searcher searcher;

  /**
   * The {@link Date} when the index was last modified.
   * 
   * @see #refresh()
   */
  private Date indexModificationDate;

  /**
   * The constructor.
   * 
   * @param searchEngineRefresher is the {@link SearchEngineRefresher}.
   * @param directory is the {@link Directory} of the search-index.
   * @param analyzer is the {@link Analyzer} to use.
   * @param queryBuilder is the {@link SearchQueryBuilder} query builder.
   * @param highlightFormatter is the {@link Formatter} for highlighting.
   */
  public LuceneSearchEngine(SearchEngineRefresher searchEngineRefresher, Directory directory,
      Analyzer analyzer, SearchQueryBuilder queryBuilder, Formatter highlightFormatter) {

    super(searchEngineRefresher);
    this.directory = directory;
    this.analyzer = analyzer;
    this.queryBuilder = queryBuilder;
    this.highlightFormatter = highlightFormatter;
    refresh();
  }

  /**
   * This method determines the modification {@link Date} of the search-index
   * specified by the given <code>directory</code>.
   * 
   * @param directory is the {@link Directory} with the search-index.
   * @return the modification {@link Date} of the search-index. Will be
   *         <code>new Date(0)</code> if the search-index does NOT exist.
   */
  protected static Date getModificationDate(Directory directory) {

    try {
      long modificationDate = 0;
      for (String filename : directory.listAll()) {
        long modified = directory.fileModified(filename);
        if (modified > modificationDate) {
          modificationDate = modified;
        }
      }
      return new Date(modificationDate);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * @return the indexModificationDate
   */
  public Date getIndexModificationDate() {

    return this.indexModificationDate;
  }

  /**
   * The constructor.
   * 
   * @param indexReader is the {@link IndexReader}.
   * @param analyzer is the {@link Analyzer} to use.
   * @param queryBuilder is the {@link SearchQueryBuilder} query builder.
   * @param highlightFormatter is the {@link Formatter} for highlighting.
   */
  public LuceneSearchEngine(IndexReader indexReader, Analyzer analyzer,
      SearchQueryBuilder queryBuilder, Formatter highlightFormatter) {

    super(null);
    this.directory = null;
    this.analyzer = analyzer;
    this.queryBuilder = queryBuilder;
    this.highlightFormatter = highlightFormatter;
    this.searcher = new IndexSearcher(indexReader);
  }

  /**
   * {@inheritDoc}
   */
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
   * This method creates a new instance of the {@link SearchHighlighter} for a
   * {@link SearchResultPage}.
   * 
   * @param luceneQuery is the lucene {@link Query} used for highlighting.
   * @return the {@link SearchHighlighter}.
   */
  protected SearchHighlighter createSearchHighlighter(Query luceneQuery) {

    return new LuceneSearchHighlighter(this.analyzer, this.highlightFormatter, luceneQuery);
  }

  /**
   * This method creates an {@link SearchHit} for the given
   * <code>documentId</code> and <code>score</code>.
   * 
   * @param documentId is the technical ID of the lucene {@link Document}
   *        representing the hit.
   * @param score is the {@link SearchHit#getScore() score} of the hit.
   * @param searchHighlighter is used to create the
   *        {@link SearchHit#getHighlightedText() highlighted text}.
   * @return the {@link SearchHit}.
   */
  protected SearchHit createSearchHit(int documentId, double score,
      SearchHighlighter searchHighlighter) {

    try {
      Document document = this.searcher.doc(documentId);
      SearchEntry searchEntry = new LuceneSearchEntry(document);
      return new SearchHitImpl(searchEntry, Integer.toString(documentId), score, searchHighlighter);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchResultPage search(SearchQuery query, int hitsPerPage) {

    return search(query, hitsPerPage, 0, -1);
  }

  /**
   * {@inheritDoc}
   */
  public SearchResultPage search(SearchQuery query, int hitsPerPage, int pageIndex,
      int totalHitCount) throws SearchException {

    try {
      Query luceneQuery = getLuceneQuery(query);
      int start = hitsPerPage * pageIndex;
      int requiredHitCount = hitsPerPage + start;
      // do the actual search...
      TopDocs topDocs = this.searcher.search(luceneQuery, requiredHitCount);
      int pageHitCount = topDocs.scoreDocs.length - start;
      SearchHit[] hits;
      if (pageHitCount > 0) {
        hits = new SearchHit[pageHitCount];
        SearchHighlighter searchHighlighter = createSearchHighlighter(luceneQuery);
        for (int i = 0; i < hits.length; i++) {
          ScoreDoc scoreDoc = topDocs.scoreDocs[start + i];
          hits[i] = createSearchHit(scoreDoc.doc, scoreDoc.score, searchHighlighter);
        }
      } else {
        hits = SearchHit.NO_HITS;
      }
      SearchResultPage page = new SearchResultPageImpl(query.toString(), topDocs.totalHits,
          hitsPerPage, 0, hits);
      return page;
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
      Date currentModificationDate = getModificationDate(this.directory);
      if (this.indexModificationDate != null) {
        if (this.indexModificationDate.equals(currentModificationDate)) {
          // index is unchanged...
          return;
        } else {
          getLogger().debug("search-index has changed and search-engine is refreshed!");
        }
      }
      if (this.searcher != null) {
        try {
          this.searcher.close();
        } catch (Exception e) {
          getLogger().error("Closing the search-engine for refresh failed!", e);
        }
        this.searcher = null;
      }
      this.searcher = new IndexSearcher(this.directory);
      this.indexModificationDate = currentModificationDate;
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

  /**
   * This inner class is used to collect the {@link #getHits() hits} and to
   * determine the {@link #getTotalHitCount() total hit count}.
   */
  protected class HitCollector extends Collector {

    /** The actual lucene {@link Query}. */
    private final Query luceneQuery;

    /** @see #getHits() */
    private final SearchHit[] hits;

    /** The current index in {@link #hits} where to add new hits. */
    private int hitCount;

    /** The score of the current hit. */
    private double currentScore;

    /** The lazy initialized {@link SearchHighlighter}. */
    private SearchHighlighter highlighter;

    /** The total number of hits. */
    private int totalHitCount;

    /** @see #setNextReader(IndexReader, int) */
    private int documentIdBase;

    /**
     * The constructor.
     * 
     * @param pageHitCount is the number of {@link #getHits() hits} to collect.
     *        All other hits are ignored.
     * @param luceneQuery is the {@link Query} used when searching for the hits
     *        to collect.
     */
    public HitCollector(int pageHitCount, Query luceneQuery) {

      this(pageHitCount, luceneQuery, 0);
    }

    /**
     * The constructor.
     * 
     * @param pageHitCount is the number of {@link #getHits() hits} to collect.
     *        All other hits are ignored.
     * @param luceneQuery is the {@link Query} used when searching for the hits
     *        to collect.
     * @param pageIndex is the {@link SearchResultPage#getPageIndex() index} of
     *        the {@link SearchResultPage page} for which the hits are
     *        collected.
     */
    public HitCollector(int pageHitCount, Query luceneQuery, int pageIndex) {

      super();
      this.luceneQuery = luceneQuery;
      this.hits = new SearchHit[pageHitCount];
      this.hitCount = -(pageIndex * pageHitCount);
      this.totalHitCount = 0;
      this.highlighter = null;
    }

    /**
     * This method gets the collected hits for the {@link SearchResultPage}.
     * 
     * @return the hits
     */
    public SearchHit[] getHits() {

      if (this.hitCount == this.hits.length) {
        return this.hits;
      } else {
        SearchHit[] result = new SearchHit[this.hitCount];
        System.arraycopy(this.hits, 0, result, 0, this.hitCount);
        return result;
      }
    }

    /**
     * @return the totalHitCount
     */
    public int getTotalHitCount() {

      return this.totalHitCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScorer(Scorer scorer) throws IOException {

      if ((this.hitCount >= 0) && (this.hitCount < this.hits.length)) {
        this.currentScore = scorer.score();
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collect(int documentId) throws IOException {

      if (this.hitCount < this.hits.length) {
        if (this.hitCount >= 0) {
          Document document = LuceneSearchEngine.this.searcher
              .doc(this.documentIdBase + documentId);
          SearchEntry searchEntry = new LuceneSearchEntry(document);
          if (this.highlighter == null) {
            this.highlighter = new LuceneSearchHighlighter(LuceneSearchEngine.this.analyzer,
                LuceneSearchEngine.this.highlightFormatter, this.luceneQuery);
          }
          this.hits[this.hitCount] = new SearchHitImpl(searchEntry, Integer.toString(documentId),
              this.currentScore, this.highlighter);
        }
        this.hitCount++;
      }
      this.totalHitCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextReader(IndexReader reader, int docBase) {

      this.documentIdBase = docBase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean acceptsDocsOutOfOrder() {

      return false;
    }

  }

}
