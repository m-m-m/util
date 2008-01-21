/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.SearchHit;
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
   * The constructor.
   * 
   * @param searchAnalyzer
   * @param formatter is the formatter used to highlight terms.
   * @param searchQuery
   */
  public LuceneSearchHighlighter(Analyzer searchAnalyzer, Formatter formatter, Query searchQuery) {

    super();
    this.highlighter = new Highlighter(formatter, new QueryScorer(searchQuery));
    this.analyzer = searchAnalyzer;
  }

  /**
   * {@inheritDoc}
   */
  public String getHighlightedText(String text) {

    String result = null;
    if (text != null) {
      try {
        // result = this.highlighter.getBestFragment(this.analyzer,
        // SearchProperties.PROPERTY_TEXT, text);

        TokenStream tokenStream = this.analyzer.tokenStream(SearchEntry.PROPERTY_TEXT,
            new StringReader(text));
        result = this.highlighter.getBestFragments(tokenStream, text, 3,
            SearchHit.HIGHLIGHT_CUT_TEXT);
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
