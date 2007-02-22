/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;

/**
 * This is the abstract base implementation of the {@link SearchEngine}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchEngine implements SearchEngine {

  /**
   * The constructor
   */
  public AbstractSearchEngine() {

    super();
  }

  /**
   * @see net.sf.mmm.search.engine.api.SearchEngine#search(net.sf.mmm.search.engine.api.SearchQuery,
   *      int)
   */
  public SearchResultPage search(SearchQuery query, int pageIndex) {

    return search(query, pageIndex, SearchResultPage.HITS_PER_PAGE);
  }

  /**
   * This method is a simple implementation of this method. Please override if
   * there is a more efficient way to do this.
   * 
   * @see net.sf.mmm.search.engine.api.SearchEngine#search(net.sf.mmm.search.engine.api.SearchQuery,
   *      int, int)
   */
  public SearchResultPage search(SearchQuery query, int pageIndex, int hitsPerPage) {

    return search(query).getPage(pageIndex, hitsPerPage);
  }

}
