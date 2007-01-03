/* $Id$ */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.base.SearchHighlighter;

/**
 * This is the implementation of the {@link SearchHighlighter} interface using
 * lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchHighlighter implements SearchHighlighter {

  /** the lucene highlighter */
  private final Highlighter highlighter;

  /** the lucene analyzer */
  private final Analyzer analyzer;

  /**
   * The constructor
   * 
   * @param searchAnalyzer
   * @param searchQuery
   */
  public LuceneSearchHighlighter(Analyzer searchAnalyzer, Query searchQuery) {

    super();
    this.highlighter = new Highlighter(new QueryScorer(searchQuery));
    this.analyzer = searchAnalyzer;
  }

  /**
   * @see net.sf.mmm.search.engine.base.SearchHighlighter#getHighlightedText(java.lang.String)
   */
  public String getHighlightedText(String text) {

    String result = null;
    if (text != null) {
      try {
        // result = this.highlighter.getBestFragment(this.analyzer,
        // SearchProperties.PROPERTY_TEXT, text);

        TokenStream tokenStream = this.analyzer.tokenStream(SearchEntry.PROPERTY_TEXT,
            new StringReader(text));
        result = this.highlighter.getBestFragments(tokenStream, text, 3, " <B>...</B>");
      } catch (IOException e) {
        result = text;
      }
    }
    if (result == null) {
      result = "";
    }
    return result;
  }

}
