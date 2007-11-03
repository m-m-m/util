/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.mmm.nls.api.NlsTranslationSource;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.nls.api.NlsTranslator} interface. It
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
public class SimpleStringTranslator extends AbstractNlsTranslator {

  /** the original bundle */
  private final AbstractResourceBundle nlsBundle;

  /** the nationalized bundle */
  private final ResourceBundle localeBundle;

  /**
   * The constructor.
   * 
   * @param internationalBundle is the NLS bundle.
   * @param locale is the locale used to
   *        {@link ResourceBundle#getBundle(String, Locale) lookup} the
   *        nationalized bundle.
   */
  public SimpleStringTranslator(AbstractResourceBundle internationalBundle, Locale locale) {

    super();
    this.nlsBundle = internationalBundle;
    this.localeBundle = ResourceBundle.getBundle(this.nlsBundle.getClass().getName(), locale);
  }

  /**
   * {@inheritDoc}
   */
  public String translate(NlsTranslationSource source) {

    String result = null;
    String key = source.getBundleKey();
    if (key == null) {
      String msg = source.getInternationalizedMessage();
      key = this.nlsBundle.getKey(msg);
      if (key != null) {
        source.setBundleKey(key);
        source.setBundleName(this.nlsBundle.getClass().getName());
      }
    }
    if (key != null) {
      result = this.localeBundle.getString(key);
    }
    return result;
  }

}
