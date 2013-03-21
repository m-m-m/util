/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.engine.api.SearchHit;

import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

/**
 * This class is the default {@link org.apache.lucene.search.highlight.Formatter}. It is just a
 * {@link SimpleHTMLFormatter} with default tags.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named(HighlightFormatter.CDI_NAME)
public class HighlightFormatter extends SimpleHTMLFormatter {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  public static final String CDI_NAME = "org.apache.lucene.search.highlight.Formatter";

  /**
   * The constructor.
   */
  public HighlightFormatter() {

    super(SearchHit.HIGHLIGHT_START_TAG, SearchHit.HIGHLIGHT_END_TAG);
  }

}
