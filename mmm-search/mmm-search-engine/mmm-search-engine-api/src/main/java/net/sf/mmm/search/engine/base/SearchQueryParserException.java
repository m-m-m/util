/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
public class SearchQueryParserException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5347162013955772955L;

  /**
   * The constructor.
   * 
   * @param message is the {@link #getNlsMessage() error message}.
   */
  public SearchQueryParserException(NlsMessage message) {

    super(message);
  }

}
