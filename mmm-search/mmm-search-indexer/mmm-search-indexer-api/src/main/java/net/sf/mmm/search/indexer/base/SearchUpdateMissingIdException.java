/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer#update(net.sf.mmm.search.indexer.api.MutableSearchEntry)}
 * if the given entry has no ID set.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchUpdateMissingIdException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1458156115842499131L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchUpdateMissingId";

  /**
   * The constructor.
   */
  public SearchUpdateMissingIdException() {

    super(getBundle().errorUpdateMissingId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
