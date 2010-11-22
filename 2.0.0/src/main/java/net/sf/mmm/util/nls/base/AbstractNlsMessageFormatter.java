/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.scanner.base.SimpleCharScannerSyntax;

/**
 * This is the abstract base implementation of the {@link NlsMessageFormatter}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractNlsMessageFormatter extends AbstractNlsFormatter<Void> implements
    NlsMessageFormatter {

  /** The syntax of the message-format patterns. */
  protected static final SimpleCharScannerSyntax SYNTAX = new SimpleCharScannerSyntax();

  static {
    SYNTAX.setQuote('\'');
    SYNTAX.setQuoteEscape('\'');
    SYNTAX.setQuoteEscapeLazy(true);
  }

}
