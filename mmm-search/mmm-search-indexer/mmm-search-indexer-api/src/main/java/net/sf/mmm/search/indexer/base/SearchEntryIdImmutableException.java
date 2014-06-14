/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown if the user tried to modify the
 * {@link net.sf.mmm.search.api.SearchEntry#getId() ID} of a {@link net.sf.mmm.search.api.SearchEntry}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchEntryIdImmutableException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5094191781995187934L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchEntryIdImmutable";

  /**
   * The constructor.
   */
  public SearchEntryIdImmutableException() {

    super(getBundle().errorIdImmutable());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
