/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from {@link net.sf.mmm.search.indexer.api.SearchIndexer} if the given entry
 * has no ID set.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchRemoveFailedException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7746309827926123365L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchRemoveFailed";

  /**
   * The constructor.
   *
   * @param property is the property used for remove.
   * @param value is the value used for remove.
   */
  public SearchRemoveFailedException(String property, String value) {

    this(null, property, value);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param property is the property used for remove.
   * @param value is the value used for remove.
   */
  public SearchRemoveFailedException(Throwable nested, String property, String value) {

    super(nested, getBundle().errorRemoveFailed(property, value));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
