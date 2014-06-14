/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the generic exception if query-parsing failed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryParseException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5347162013955772955L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchQueryParse";

  /**
   * The constructor.
   *
   * @param message
   */
  public SearchQueryParseException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested
   * @param message
   */
  public SearchQueryParseException(Throwable nested, NlsMessage message) {

    super(nested, message);
    // TODO Auto-generated constructor stub
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this message.
   * @param query is the illegal query.
   */
  public SearchQueryParseException(Throwable nested, String query) {

    super(nested, getBundle().errorQueryParse(query));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
