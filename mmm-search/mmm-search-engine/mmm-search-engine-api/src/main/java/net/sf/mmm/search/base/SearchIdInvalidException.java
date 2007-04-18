/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsBundleSearchApi;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown if a given
 * {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() entry ID} is
 * invalid.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchIdInvalidException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3613794146407350907L;

  /**
   * The constructor. 
   * 
   * @param entryId
   *        is the invalid entry ID.
   */
  public SearchIdInvalidException(String entryId) {

    super(NlsBundleSearchApi.ERR_ID_INVALID, entryId);
  }

  /**
   * The constructor. 
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param entryId
   *        is the invalid entry ID.
   */
  public SearchIdInvalidException(Throwable nested, String entryId) {

    super(nested, NlsBundleSearchApi.ERR_ID_INVALID, entryId);
  }

}
