/* $Id$ */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;

import org.apache.lucene.search.Hits;

import net.sf.mmm.search.engine.api.SearchException;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.base.AbstractSearchResult;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.engine.base.SearchHitImpl;
import net.sf.mmm.search.impl.LuceneSearchEntry;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchResult} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchResult extends AbstractSearchResult {

  /** the lucene hits */
  private final Hits hits;

  /** the highlighter */
  private final SearchHighlighter highlighter;

  /**
   * The constructor
   * 
   * @param searchQuery
   * @param searchHits
   * @param searchHighlighter
   */
  public LuceneSearchResult(String searchQuery, Hits searchHits, SearchHighlighter searchHighlighter) {

    super(searchQuery);
    this.hits = searchHits;
    this.highlighter = searchHighlighter;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResult#getHit(int)
   */
  public SearchHit getHit(int index) {

    try {
      String entryId = Integer.toString(this.hits.id(index));
      return new SearchHitImpl(new LuceneSearchEntry(this.hits.doc(index)), entryId, this.hits
          .score(index), this.highlighter);
    } catch (IOException e) {
      throw new SearchException(e, "Failed to get hit!");
    }
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchResult#getHitCount()
   */
  public int getHitCount() {

    return this.hits.length();
  }

}
