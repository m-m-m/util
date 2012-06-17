/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchQueryBuilderOptions;
import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;

/**
 * This is the implementation of {@link SearchQueryBuilderOptions} as java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryBuilderOptionsBean implements SearchQueryBuilderOptions {

  /** @see #isRequireTerms() */
  private boolean requireTerms;

  /** @see #getErrorHandler() */
  private SearchQueryErrorHandler errorHander;

  /**
   * The constructor.
   */
  public SearchQueryBuilderOptionsBean() {

    super();
    this.requireTerms = false;
    this.errorHander = new SearchQueryErrorHandlerFail();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRequireTerms() {

    return this.requireTerms;
  }

  /**
   * @param requireTerms is the requireTerms to set
   */
  public void setRequireTerms(boolean requireTerms) {

    this.requireTerms = requireTerms;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchQueryErrorHandler getErrorHandler() {

    return this.errorHander;
  }

  /**
   * @param errorHander is the errorHander to set
   */
  public void setErrorHander(SearchQueryErrorHandler errorHander) {

    this.errorHander = errorHander;
  }

}
