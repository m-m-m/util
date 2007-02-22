/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl;

import org.apache.lucene.search.Query;

import net.sf.mmm.search.engine.api.SearchQuery;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchQuery} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLuceneSearchQuery implements SearchQuery {

  /**
   * The constructor
   */
  public AbstractLuceneSearchQuery() {

    super();
  }

  /**
   * This method gets the native lucene query.
   * 
   * @return the lucene query.
   */
  public abstract Query getLuceneQuery();
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return getLuceneQuery().toString();
  }

}
