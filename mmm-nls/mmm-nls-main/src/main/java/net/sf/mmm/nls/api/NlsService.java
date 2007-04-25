/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

import java.util.Locale;

/**
 * This is the interface of a service that provides native language support
 * (NLS). This is done by a {@link NlsTranslator} that can be
 * {@link #getTranslator(Locale) retrieved} for a given {@link Locale}.
 * 
 * @see NlsMessage#getLocalizedMessage(NlsTranslator)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsService {

  /**
   * This method gets the {@link NlsTranslator} for the given
   * <code>locale</code>.
   * 
   * @param locale
   *        is the locale that should be translated to.
   * @return the according translator.s
   */
  NlsTranslator getTranslator(Locale locale);

}
