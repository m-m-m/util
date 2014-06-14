/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import javax.servlet.ServletRequest;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This class allows to {@link #getContext(ServletRequest) get} the {@link SearchViewContext} from anywhere
 * via {@link ServletRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class SearchViewContextAccess {

  /**
   * Construction forbidden.
   */
  private SearchViewContextAccess() {

    super();
  }

  /**
   * This method gets the {@link SearchViewContext} from the given <code>request</code>.
   * 
   * @param request is the request where to get the {@link SearchViewContext} from.
   * @return the {@link SearchViewContext} instance.
   */
  public static SearchViewContext getContext(ServletRequest request) {

    SearchViewContext result = (SearchViewContext) request.getAttribute(SearchViewContext.KEY);
    if (result == null) {
      throw new ObjectNotFoundException(SearchViewContext.class, SearchViewContext.KEY);
    }
    return result;
  }

}
