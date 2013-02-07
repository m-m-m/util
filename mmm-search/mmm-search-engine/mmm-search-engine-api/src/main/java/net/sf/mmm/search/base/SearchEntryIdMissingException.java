/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsBundleSearchApi;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from {@link net.sf.mmm.search.engine.api.SearchEngine#getEntry(String)} if the
 * given {@link net.sf.mmm.search.engine.api.SearchHit#getId() entry ID} does NOT exist in the index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchEntryIdMissingException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 301902932006670136L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchEntryIdMissing";

  /**
   * The constructor.
   * 
   * @param entryId is the invalid entry ID.
   */
  public SearchEntryIdMissingException(String entryId) {

    super(NlsBundleSearchApi.ERR_ENTRY_ID_MISSING, toMap(KEY_ID, entryId));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param entryId is the invalid entry ID.
   */
  public SearchEntryIdMissingException(Throwable nested, String entryId) {

    super(nested, NlsBundleSearchApi.ERR_ENTRY_ID_MISSING, toMap(KEY_ID, entryId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
