/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.Collections;

import net.sf.mmm.search.NlsBundleSearchApi;
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

  /**
   * The constructor.
   * 
   * @param message is the {@link #getNlsMessage() error message}.
   */
  public SearchQueryParseException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this message.
   * @param message is the {@link #getNlsMessage() error message}.
   */
  public SearchQueryParseException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this message.
   */
  public SearchQueryParseException(Throwable nested) {

    super(nested, NlsBundleSearchApi.ERR_QUERY_PARSE, Collections.EMPTY_MAP);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this message.
   * @param query is the illegal query.
   */
  public SearchQueryParseException(Throwable nested, String query) {

    super(nested, NlsBundleSearchApi.ERR_QUERY_PARSE_WITH_QUERY, toMap(KEY_QUERY, query));
  }

}
