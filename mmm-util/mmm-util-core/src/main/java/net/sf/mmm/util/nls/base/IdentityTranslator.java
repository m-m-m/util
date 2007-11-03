/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsTranslationSource;
import net.sf.mmm.util.nls.api.NlsTranslator;

/**
 * This is an implementation of the StringTranslatorIF interface that simply
 * returns the given string untranslated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class IdentityTranslator extends AbstractNlsTranslator {

  /** the singleton instance */
  public static final NlsTranslator INSTANCE = new IdentityTranslator();

  /**
   * The constructor.
   */
  private IdentityTranslator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String translate(NlsTranslationSource source) {

    return source.getInternationalizedMessage();
  }

}
