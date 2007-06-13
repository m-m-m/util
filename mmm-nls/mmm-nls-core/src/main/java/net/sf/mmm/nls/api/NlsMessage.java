/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

/**
 * This is the interface for an internationalized message. It stores a message
 * separated from language independent {@link #getArgument(int) arguments}.
 * This approach ensures that the message is always available in the
 * internationalized language (should be English) while it still allows to
 * {@link #getLocalizedMessage(NlsTranslator) translate} the message to a native
 * language.<br>
 * For the term <em>internationalization</em> usually the shortcut
 * <em>i18n</em> is used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsMessage extends NlsObject {

  /**
   * This method gets the number of language independent arguments of this
   * exception.
   * 
   * @return the argument count.
   */
  int getArgumentCount();

  /**
   * This method gets the language independent argument at the given
   * <code>index</code>.
   * 
   * @param index is the position of the requested argument.
   * @return the argument at the given index.
   */
  Object getArgument(int index);

  /**
   * This method gets the untranslated message (default language should be
   * English) with arguments filled in.
   * 
   * @see NlsMessage#getLocalizedMessage(NlsTranslator)
   * 
   * @return the i18n message with arguments filled in.
   */
  String getMessage();

  /**
   * This method tries to get the localized message as string. Since no
   * translator is specified, the implementation should try its best to do the
   * translation on its own according to the callers
   * {@link java.util.Locale locale}. Therefore this method has to do some
   * magic behind the scenes. This may only work if you follow specific rules of
   * the implementation of this interface. If this fails or is NOT supported,
   * the {@link #getMessage() untranslated} message should be used.<br>
   * <b>ATTENTION:</b> If possible try to avoid using this method and use
   * {@link #getLocalizedMessage(NlsTranslator)} instead.
   * 
   * @return the localized message.
   */
  String getLocalizedMessage();

  /**
   * This method gets the localized message as string.
   * 
   * @see NlsMessage#getLocalizedMessage(NlsTranslator, StringBuffer)
   * 
   * @param nationalizer is used to translate the message.
   * @return the localized message.
   */
  String getLocalizedMessage(NlsTranslator nationalizer);

  /**
   * This method writes the localized message to the given <code>buffer</code>.
   * <br>
   * The actual localization is done by the given <code>translator</code>. If
   * this translator fails (returns <code>null</code>), the original message
   * (in English language) will be used. After translation is done, the language
   * independent arguments will be filled in the translated message string
   * according to the locale of the translated message.
   * 
   * @param nationalizer is used to translates the original message.
   * @param buffer is the buffer where to write the message to.
   */
  void getLocalizedMessage(NlsTranslator nationalizer, StringBuffer buffer);

}
