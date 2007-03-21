/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl;

import org.apache.lucene.search.Query;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchQuery} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchQuery extends AbstractLuceneSearchQuery {

  /** the actual lucene query */
  private Query query;

  /**
   * The constructor
   * 
   * @param luceneQuery
   *        is the actual lucene query to wrap.
   */
  public LuceneSearchQuery(Query luceneQuery) {

    super();
    this.query = luceneQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query getLuceneQuery() {

    return this.query;
  }

}
