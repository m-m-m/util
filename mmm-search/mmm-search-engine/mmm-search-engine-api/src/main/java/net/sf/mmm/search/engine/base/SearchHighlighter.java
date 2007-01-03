/* $Id$ */
package net.sf.mmm.search.engine.base;

/**
 * This is the interface of the highlighter used for
 * {@link net.sf.mmm.search.engine.api.SearchHit#getHighlightedText()}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchHighlighter {

  /**
   * This method highlights the given <code>text</code>.
   * 
   * @see net.sf.mmm.search.engine.api.SearchHit#getHighlightedText()
   * 
   * @param text
   *        is the plain text to highlight.
   * @return the highlighted text.
   */
  String getHighlightedText(String text);

}
