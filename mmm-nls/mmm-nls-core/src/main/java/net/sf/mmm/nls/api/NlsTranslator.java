/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

import java.text.MessageFormat;

/**
 * This is the callback interface for translating a text-message to a
 * {@link java.util.Locale locale}-specific language.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsTranslator {

  /**
   * This method translates a given message string.<br>
   * Instead of using keys for direct lookup of the message from a
   * {@link java.util.ResourceBundle} this method also works with the
   * {@link NlsTranslationSource#getInternationalizedMessage() internationalized message}.
   * An implementation may use this message for reverse mapping to a message-key
   * and then perform a translation by forward mapping that key to the
   * nationalized message. To ensure that this will work, the original message
   * needs to be supplied from the constant defined by the
   * {@link net.sf.mmm.nls.base.AbstractResourceBundle bundle}.<br>
   * To speed up repetitive translations of the same source the implementation
   * should store the key from the reverse lookup.<br>
   * A legal implementation of this interface does NOT throw
   * {@link java.lang.Exception anything}. On multiple calls it should always
   * return the {@link String#equals(java.lang.Object) same} result for the same
   * argument (except the localized bundles have been updated and the
   * implementation supports reloading this at runtime).
   * 
   * @see net.sf.mmm.nls.base.AbstractResourceBundle
   * @see NlsTranslationSource#getInternationalizedMessage()
   * 
   * @param source
   *        is the source-data to translate.
   * @return the translation of the given message or <code>null</code> if no
   *         translation is available.
   */
  String translate(NlsTranslationSource source);

  /**
   * This method behaves like {@link #translate(NlsTranslationSource)} but
   * returns the translated message as {@link MessageFormat}. This allows to
   * construct the {@link MessageFormat} with the appropriate
   * {@link java.util.Locale} and gives ability for advanced caching strategies.
   * 
   * @param source
   *        is the source-data to translate.
   * @return the translation of the given message or <code>null</code> if no
   *         translation is available.
   */
  MessageFormat translateFormat(NlsTranslationSource source);

}
