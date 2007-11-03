/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the callback interface for translating a text-message to a
 * {@link java.util.Locale}-specific language.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsTranslator {

  /**
   * This method translates a given <code>source</code>.<br>
   * Instead of using keys for direct lookup of the message from a
   * {@link java.util.ResourceBundle} this method also works with the
   * {@link NlsTranslationSource#getInternationalizedMessage() internationalized message}.
   * An implementation may use this message for reverse mapping to a message-key
   * and then perform a translation by forward mapping that key to the
   * nationalized message. To ensure that this will work, the original message
   * needs to be supplied from the constant defined by the
   * {@link net.sf.mmm.util.nls.base.AbstractResourceBundle bundle}.<br>
   * To speed up repetitive translations of the same source the implementation
   * should store the key from the reverse lookup.<br>
   * A legal implementation of this interface does NOT throw
   * {@link java.lang.Exception anything}. On multiple calls it should always
   * return the {@link String#equals(java.lang.Object) same} result for the same
   * argument (except the localized bundles have been updated and the
   * implementation supports reloading this at runtime).
   * 
   * @see net.sf.mmm.util.nls.base.AbstractResourceBundle
   * @see NlsTranslationSource#getInternationalizedMessage()
   * 
   * @param source contains the source-data to translate.
   * @return the translation of the given message or <code>null</code> if no
   *         translation is available.
   */
  String translate(NlsTranslationSource source);

  /**
   * This method behaves like {@link #translate(NlsTranslationSource)} but
   * additionally fills the given <code>arguments</code> into the translated
   * message using {@link java.text.MessageFormat}.
   * 
   * @param source contains the source-data to translate.
   * @param arguments are the variable arguments to fill in the message.
   * @param messageBuffer is the buffer where the translation will be appended
   *        to.
   * @return <code>true</code> if the (translated) message has been appended
   *         to the given <code>messageBuffer</code> or <code>false</code>
   *         if the translation failed.
   */
  boolean translate(NlsTranslationSource source, Object[] arguments, StringBuffer messageBuffer);

}
