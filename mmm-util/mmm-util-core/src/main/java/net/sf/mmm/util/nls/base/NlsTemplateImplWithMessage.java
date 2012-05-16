/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * This class extends {@link NlsTemplateImpl} with the
 * {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalized message} as
 * fallback.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public class NlsTemplateImplWithMessage extends NlsTemplateImpl {

  /**
   * The {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalized message}.
   */
  private final String message;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the bundle.
   * @param key is the {@link #getKey() key} of the string to lookup in the bundle.
   * @param message is the {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalized message}.
   */
  public NlsTemplateImplWithMessage(String name, String key, String message) {

    super(name, key);
    this.message = message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String translate(Locale locale) {

    if ((this.message != null) && (locale == AbstractNlsMessage.LOCALE_ROOT)) {
      return this.message;
    }
    return super.translate(locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String translateFallback(MissingResourceException e) {

    if (this.message != null) {
      return this.message;
    }
    return super.translateFallback(e);
  }

}
