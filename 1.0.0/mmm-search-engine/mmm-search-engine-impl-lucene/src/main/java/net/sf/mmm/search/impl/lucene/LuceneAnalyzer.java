/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import org.apache.lucene.analysis.Analyzer;

/**
 * This is the interface of a micro-component that {@link #getAnalyzer()
 * determines} the {@link Analyzer} instance to use.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface LuceneAnalyzer {

  /**
   * This method gets the {@link Analyzer} to use.
   * 
   * @return the {@link Analyzer} to use.
   */
  Analyzer getAnalyzer();

}
