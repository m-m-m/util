/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.mmm.nls.api.StringTranslator;

/**
 * This is a simple implementation of the {@link StringTranslator} interface. It
 * {@link ResourceBundle#getBundle(String, Locale) looks} for a regular
 * {@link ResourceBundle bundle} with the same {@link Class#getName() name} as
 * the given {@link AbstractResourceBundle nls-bundle} using the given
 * {@link Locale}.
 * 
 * @see net.sf.mmm.nls.api.NlsMessage
 * @see ResourceBundle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleStringTranslator implements StringTranslator {

  /** the original bundle */
  private final AbstractResourceBundle nlsBundle;

  /** the nationalized bundle */
  private final ResourceBundle localeBundle;

  /**
   * The constructor.
   * 
   * @param internationalBundle
   *        is the NLS bundle.
   * @param locale
   *        is the locale used to
   *        {@link ResourceBundle#getBundle(String, Locale) lookup} the
   *        nationalized bundle.
   */
  public SimpleStringTranslator(AbstractResourceBundle internationalBundle, Locale locale) {

    super();
    this.nlsBundle = internationalBundle;
    this.localeBundle = ResourceBundle.getBundle(this.nlsBundle.getClass().getName(), locale);
  }

  /**
   * @see net.sf.mmm.nls.api.StringTranslator#translate(java.lang.String)
   */
  public String translate(String message) {

    String result = message;
    String key = this.nlsBundle.getKey(message);
    if (key != null) {
      Object localMessage = this.localeBundle.getObject(key);
      if (localMessage != null) {
        result = localMessage.toString();
      }
    }
    return result;
  }

}
