/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.base.config.SearchSourceBean;
import net.sf.mmm.search.view.api.SearchSourceView;

/**
 * This is the implementation of {@link SearchSourceView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class SearchSourceViewBean extends SearchSourceBean implements SearchSourceView {

  /** @see #getEntryCount() */
  private long entryCount;

  /**
   * The constructor.
   */
  public SearchSourceViewBean() {

    super();
  }

  /**
   * The copy constructor.
   * 
   * @param template is the {@link SearchSource} to copy.
   */
  public SearchSourceViewBean(SearchSource template) {

    super(template);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getEntryCount() {

    return this.entryCount;
  }

  /**
   * @param entryCount is the entryCount to set
   */
  public void setEntryCount(long entryCount) {

    this.entryCount = entryCount;
  }

}
