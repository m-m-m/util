/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * This is the {@link StandardAnalyzer} annotated with {@link Named} to be auto
 * wired as component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
@Singleton
public class DefaultAnalyzer extends StandardAnalyzer {

  /**
   * The constructor.
   */
  public DefaultAnalyzer() {

    super();
  }

}
