/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

  /** @see #getEntryId() */
  private final String entryId;

  /** @see #getScore() */
  private final double score;

  /** the highlighter */
  private final SearchHighlighter highlighter;

  /**
   * The constructor.
   * 
   * @param searchEntry is the actual entry this hit delegates to.
   * @param id is the {@link #getEntryId() entry ID}.
   * @param hitScore is the {@link #getScore() score} of the hit.
   * @param searchHighlighter is the highlighter used for
   *        {@link #getHighlightedText() highlighting}.
   */
  public SearchHitImpl(SearchEntry searchEntry, String id, double hitScore,
      SearchHighlighter searchHighlighter) {

    super();
    this.entry = searchEntry;
    this.entryId = id;
    this.score = hitScore;
    this.highlighter = searchHighlighter;
  }

  /**
   * {@inheritDoc}
   */
  public double getScore() {

    return this.score;
  }

  /**
   * {@inheritDoc}
   */
  public int getScore(int maximum) {

    return (int) (getScore() * maximum);
  }

  /**
   * {@inheritDoc}
   */
  public String getHighlightedText() {

    return this.highlighter.getHighlightedText(getText());
  }

  /**
   * {@inheritDoc}
   */
  public String getEntryId() {

    return this.entryId;
  }

  /**
   * {@inheritDoc}
   */
  public String getProperty(String name) {

    return this.entry.getProperty(name);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<String> getPropertyNames() {

    return this.entry.getPropertyNames();
  }

}
