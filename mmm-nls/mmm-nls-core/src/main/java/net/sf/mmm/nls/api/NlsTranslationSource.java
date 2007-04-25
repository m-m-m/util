/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

/**
 * This is the interface for the source-data of a {@link NlsMessage NLS message}.
 * It holds the {@link #getInternationalizedMessage() internationalized message}
 * (in English language).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsTranslationSource {

  /**
   * This method gets the internationalized message that can be
   * {@link NlsTranslator#translate(NlsTranslationSource) translated} to a
   * native language. The language independent arguments are filled into the
   * message after the translation process. <br>
   * E.g. the i18n message may be <code>"Welcome {0}!"</code> and there is one
   * argument that is the string <code>"Joelle"</code>. The final result will
   * then be <code>"Welcome Joelle!"</code>. If the message is translated to
   * German as <code>"Willkommen {0}!"</code> the final result will be
   * <code>"Willkommen Joelle!"</code>.
   * 
   * @see NlsMessage#getArgument(int)
   * @see java.text.MessageFormat
   * 
   * @return the message for internationalization.
   */
  String getInternationalizedMessage();

  /**
   * This method gets the {@link java.util.ResourceBundle#getString(String) key}
   * (technical UID) of the
   * {@link #getInternationalizedMessage() internationalized message}.
   * 
   * @return the message key or <code>null</code> if NOT available.
   */
  String getBundleKey();

  /**
   * This method gets the
   * {@link java.util.ResourceBundle#getBundle(String) base name} of the bundle
   * containing the message.
   * 
   * @return the bundle name or <code>null</code> if NOT available.
   */
  String getBundleName();

  /**
   * This method sets the {@link #getBundleName() bundle name}. If it is
   * already set, this method will have no effect.
   * 
   * @param bundleName
   *        is the bundle name to set.
   */
  void setBundleName(String bundleName);

  /**
   * This method sets the {@link #getBundleKey() bundle key}. If it is already
   * set, this method will have no effect.
   * 
   * @param bundleKey
   *        is the key to set.
   */
  void setBundleKey(String bundleKey);

}
