/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.search.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This is the exception thrown if an operation (typically a query) in the persistence exceeded a configured
 * timeout.<br>
 * This typically means that the end-user should refine his query and rerun the search.
 * 
 * @see net.sf.mmm.util.search.api.SearchCriteria#getSearchTimeout()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class SearchTimeoutException extends NlsRuntimeException {

  /** UID for serialization.. */
  private static final long serialVersionUID = 4019398692999534503L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchTimeout";

  /**
   * The constructor.
   */
  public SearchTimeoutException() {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorSearchTimeout());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
