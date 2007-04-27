/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.text.MessageFormat;
import java.util.Locale;

import net.sf.mmm.nls.api.NlsTranslationSource;
import net.sf.mmm.nls.api.NlsTranslator;

/**
 * This is the abstract base implementation of the {@link NlsTranslator}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNlsTranslator implements NlsTranslator {

  /**
   * This method gets the locale used by
   * {@link #translateFormat(NlsTranslationSource)}.
   * 
   * @return the locale of this translator.
   */
  protected Locale getLocale() {

    return Locale.getDefault();
  }

  /**
   * {@inheritDoc}
   */
  public boolean translate(NlsTranslationSource source, Object[] arguments,
      StringBuffer messageBuffer) {

    String message = translate(source);
    if (message == null) {
      return false;
    } else {
      MessageFormat format = new MessageFormat(message);
      format.format(arguments, messageBuffer, null);
      return true;
    }

  }

}
