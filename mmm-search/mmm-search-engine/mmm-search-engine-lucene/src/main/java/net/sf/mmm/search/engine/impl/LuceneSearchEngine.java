/* $Id$ */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchIdInvalidException;
import net.sf.mmm.search.base.SearchEntryIdMissingException;
import net.sf.mmm.search.base.SearchIoException;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResult;
import net.sf.mmm.search.engine.base.AbstractSearchEngine;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.impl.LuceneSearchEntry;

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

  /** @see #setAnalyzier(Analyzer) */
  private Analyzer analyzier;

  /** the lucene query parser */
  private QueryParser queryParser;

  /** @see #setSearcher(Searcher) */
  private Searcher searcher;

  /** @see #setIndexPath(String) */
  private String indexPath;

  /** @see #setIgnoreLeadingWildcards(boolean) */
  private boolean ignoreLeadingWildcards;

  /**
   * The constructor
   */
  public LuceneSearchEngine() {

    super();
    this.analyzier = null;
    this.queryParser = null;
    this.searcher = null;
    this.indexPath = null;
    this.queryBuilder = null;
    this.ignoreLeadingWildcards = true;
  }

  /**
   * This method sets the lucene analyser used by this search engine.
   * 
   * @param luceneAnalyzier
   *        the analyzier to set
   */
  public void setAnalyzier(Analyzer luceneAnalyzier) {

    this.analyzier = luceneAnalyzier;
  }

  /**
   * This method sets the lucene searcher used by this search engine.
   * 
   * @param luceneSearcher
   *        the searcher to set
   */
  public void setSearcher(Searcher luceneSearcher) {

    this.searcher = luceneSearcher;
  }

  /**
   * This method sets the path in the local filesystem where the search-index is
   * located. You can also use {@link #setSearcher(Searcher)} instead.
   * 
   * @param searchIndexPath
   *        the indexPath to set
   */
  public void setIndexPath(String searchIndexPath) {

    this.indexPath = searchIndexPath;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchEngine#getQueryBuilder()
   */
  public SearchQueryBuilder getQueryBuilder() {

    return this.queryBuilder;
  }

  /**
   * This method sets the {@link #getQueryBuilder() query-builder}.
   * 
   * @param searchQueryBuilder
   *        the queryBuilder to set
   */
  public void setQueryBuilder(SearchQueryBuilder searchQueryBuilder) {

    this.queryBuilder = searchQueryBuilder;
  }

  /**
   * @see #setIgnoreLeadingWildcards(boolean)
   * 
   * @return <code>true</code> if leading wildcards ('*' or '?') are ignored,
   *         <code>false</code> otherwise.
   */
  public boolean isIgnoreLeadingWildcards() {

    return this.ignoreLeadingWildcards;
  }

  /**
   * This method sets the flag to ignore leading wildcards ('*' or '?') in
   * search terms.<br>
   * <b>ATTENTION:</b><br>
   * Leading wildcards can potentially cause very expensive search queries that
   * may kill your performance. Do NOT use this for a public search site because
   * it allows simplistic DOS-Attacks.
   * 
   * @see SearchQueryBuilder#parseStandardQuery(String)
   * 
   * @param ignore -
   *        if <code>true</code>, leading wildcards ('*' or '?') are ignored,
   *        <code>false</code> otherwise.
   */
  public void setIgnoreLeadingWildcards(boolean ignore) {

    this.ignoreLeadingWildcards = ignore;
  }

  /**
   * This method has to be called before the search-engine can be used. You have
   * to inject the {@link #setIndexPath(String) index-path} or the
   * {@link #setSearcher(Searcher) searcher} before you can call this method.
   * 
   * @throws Exception
   *         if the initialization fails for arbitrary reasons.
   */
  @PostConstruct
  public void initialize() throws Exception {

    if (this.analyzier == null) {
      this.analyzier = new StandardAnalyzer();
    }
    if (this.queryParser == null) {
      this.queryParser = new QueryParser(SearchEntry.PROPERTY_TEXT, this.analyzier);
    }
    if (this.searcher == null) {
      if (this.indexPath == null) {
        throw new IllegalStateException();
      }
      this.searcher = new IndexSearcher(this.indexPath);
    }
    if (this.queryBuilder == null) {
      this.queryBuilder = new LuceneSearchQueryBuilder(this.analyzier, this.ignoreLeadingWildcards);
    }
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchEngine#search(net.sf.mmm.search.engine.api.SearchQuery)
   */
  public SearchResult search(SearchQuery query) {

    try {
      Query luceneQuery = ((AbstractLuceneSearchQuery) query).getLuceneQuery();
      Hits hits = this.searcher.search(luceneQuery);
      SearchHighlighter highlighter;
      if (hits.length() > 0) {
        highlighter = new LuceneSearchHighlighter(this.analyzier, luceneQuery);
      } else {
        highlighter = null;
      }
      return new LuceneSearchResult(query.toString(), hits, highlighter);
    } catch (IOException e) {
      throw new SearchIoException(e);
    }
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchEngine#getEntry(java.lang.String)
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
      throw new SearchIdInvalidException(e, id);
    } catch (IOException e) {
      throw new SearchIoException(e);
    }
  }

}
