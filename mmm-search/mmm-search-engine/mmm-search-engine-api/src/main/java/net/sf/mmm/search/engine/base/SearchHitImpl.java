/* $Id$ */
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
   * The constructor
   * 
   * @param searchEntry
   *        is the actual entry this hit delegates to.
   * @param id
   *        is the {@link #getEntryId() entry ID}.
   * @param hitScore
   *        is the {@link #getScore() score} of the hit.
   * @param searchHighlighter
   *        is the highlighter used for
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
   * @see net.sf.mmm.search.engine.api.SearchHit#getScore()
   */
  public double getScore() {

    return this.score;
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchHit#getScore(int)
   */
  public int getScore(int maximum) {

    return (int) (getScore() * maximum);
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchHit#getHighlightedText()
   */
  public String getHighlightedText() {

    return this.highlighter.getHighlightedText(getText());
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchHit#getEntryId()
   */
  public String getEntryId() {

    return this.entryId;
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getProperty(java.lang.String)
   */
  public String getProperty(String name) {

    return this.entry.getProperty(name);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getPropertyNames()
   */
  public Iterator<String> getPropertyNames() {

    return this.entry.getPropertyNames();
  }

}
