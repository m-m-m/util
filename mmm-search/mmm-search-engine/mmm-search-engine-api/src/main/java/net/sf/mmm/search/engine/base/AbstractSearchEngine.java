/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base implementation of the {@link ManagedSearchEngine}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchEngine extends AbstractLoggable implements ManagedSearchEngine {

  /**
   * The constructor.
   */
  public AbstractSearchEngine() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SearchResultPage search(SearchQuery query, int pageIndex) {

    return search(query, pageIndex, SearchResultPage.HITS_PER_PAGE);
  }

  /**
   * {@inheritDoc}
   * 
   * This method is a simple implementation of this method. Please override if
   * there is a more efficient way to do this.
   */
  public SearchResultPage search(SearchQuery query, int pageIndex, int hitsPerPage) {

    return search(query).getPage(pageIndex, hitsPerPage);
  }

}
