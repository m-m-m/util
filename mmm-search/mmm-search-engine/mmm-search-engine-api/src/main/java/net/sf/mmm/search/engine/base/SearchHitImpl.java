/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.Iterator;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.base.AbstractSearchEntry;
import net.sf.mmm.search.engine.api.SearchHit;

/**
 * This is the abstract base implementation of the {@link SearchHit} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchHitImpl extends AbstractSearchEntry implements SearchHit {

  /** the actual entry this hit delegates to */
  private final SearchEntry entry;

  /** @see #getId() */
  private final String entryId;

  /** @see #getScore() */
  private final double score;

  /** the highlighter */
  private final SearchHighlighter highlighter;

  /**
   * The constructor.
   * 
   * @param searchEntry is the actual entry this hit delegates to.
   * @param entryId is the {@link #getEntryId() entry-ID}.
   * @param hitScore is the {@link #getScore() score} of the hit.
   * @param searchHighlighter is the highlighter used for {@link #getHighlightedText() highlighting}.
   */
  public SearchHitImpl(SearchEntry searchEntry, String entryId, double hitScore, SearchHighlighter searchHighlighter) {

    super();
    this.entry = searchEntry;
    this.entryId = entryId;
    // lucene can produce scores that are higher than 1, this makes no sense...
    this.score = StrictMath.min(hitScore, 1);
    this.highlighter = searchHighlighter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getScore() {

    return this.score;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getScore(int maximum) {

    return (int) (getScore() * maximum);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHighlightedText() {

    return this.highlighter.getHighlightedText(getText());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getEntryId() {

    return this.entryId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFieldAsString(String name) {

    return this.entry.getFieldAsString(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getField(String name) {

    return this.entry.getField(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T getField(String name, Class<T> type) {

    return this.entry.getField(name, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<String> getFieldNames() {

    return this.entry.getFieldNames();
  }

}
