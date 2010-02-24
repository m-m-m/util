/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;

import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.base.AbstractSearchResult;
import net.sf.mmm.search.engine.base.SearchHighlighter;
import net.sf.mmm.search.engine.base.SearchHitImpl;
import net.sf.mmm.search.impl.LuceneSearchEntry;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.search.Hits;

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
   * The constructor.
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
   * {@inheritDoc}
   */
  public SearchHit getHit(int index) {

    try {
      String entryId = Integer.toString(this.hits.id(index));
      return new SearchHitImpl(new LuceneSearchEntry(this.hits.doc(index)), entryId, this.hits
          .score(index), this.highlighter);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getHitCount() {

    return this.hits.length();
  }

}
