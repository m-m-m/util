/* $Id$ */
package net.sf.mmm.nls.api;

/**
 * This is the interface for an internationalized message. <br>
 * For the term <em>internationalization</em> usually the shortcut
 * <em>i18n</em> is used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsMessageIF extends NlsObjectIF {

    /**
     * This method gets the internationalized message that can be nationalized (
     * {@link StringTranslatorIF#translate(String) translated} to the locales
     * language). The language independent arguments are filled into the message
     * after that nationalization process. <br>
     * E.g. the i18n message may be <code>"Welcome {0}!"</code> and there is
     * one argument that is the string <code>"Joelle"</code>. The final
     * result will then be <code>"Welcome Joelle!"</code>. But the message
     * can also be translated to german as <code>"Willkommen {0}!"</code> and
     * finally result in <code>"Willkommen Joelle!"</code>.
     * 
     * @see NlsMessageIF#getArgument(int)
     * @see java.text.MessageFormat
     * 
     * @return the message for internationalization.
     */
    String getInternationalizedMessage();

    /**
     * This method gets the number of language independed arguments of this
     * exception.
     * 
     * @return the argument count.
     */
    int getArgumentCount();

    /**
     * This method gets the language independed argument at the given index.
     * 
     * @param index
     *        is the position of the requested argument.
     * @return the argument at the given index.
     */
    Object getArgument(int index);

    /**
     * This method gets the untranslated message (default language should be
     * english) with arguments filled in.
     * 
     * @see NlsMessageIF#getLocalizedMessage(StringTranslatorIF)
     * 
     * @return the i18n message with arguments filled in.
     */
    String getMessage();

    /**
     * This method tries to get the localized message as string. It simply calls
     * {@link NlsMessageIF#getLocalizedMessage(StringTranslatorIF)} will
     * <code>null</code> as argument.
     * 
     * @return the localized message.
     */
    String getLocalizedMessage();

    /**
     * This method gets the localized message as string.
     * 
     * @see NlsMessageIF#getLocalizedMessage(StringTranslatorIF, StringBuffer)
     * 
     * @param nationalizer
     *        is used to translates the original
     *        {@link NlsMessageIF#getInternationalizedMessage() "internationalized message"}.
     *        If <code>null</code> is given, the implementation should try its
     *        best to do the translation on its own according to the callers
     *        {@link java.util.Locale locale}. Therefore this method has to do
     *        some magic behind the scenes. This may only work if you follow
     *        specific rules of the implementation of this interface. If this
     *        fails or is NOT supported, the {@link #getMessage() untranslated}
     *        message should be used.
     * @return the localized message.
     */
    String getLocalizedMessage(StringTranslatorIF nationalizer);

    /**
     * This method writes the nationalized (or localized) message to the given
     * string buffer. <br>
     * The actual nationalization is done by the given translator who will get
     * the
     * {@link NlsMessageIF#getInternationalizedMessage() internationalized-message}.
     * If this translator fails (returns <code>null</code>), the original
     * message (in english language) will be used. After translation is done,
     * the language independed arguments will be filled in the translated
     * message string.
     * 
     * @param nationalizer
     *        is used to translates the original
     *        {@link NlsMessageIF#getInternationalizedMessage() "internationalized message"}.
     *        If <code>null</code> is given, the implementation should try its
     *        best to do the translation on its own according to the callers
     *        {@link java.util.Locale locale}. Therefore this method has to do
     *        some magic behind the scenes. This may only work if you follow
     *        specific rules of the implementation of this interface. If this
     *        fails or is NOT supported, the {@link #getMessage() untranslated}
     *        message should be used.
     * @param message
     *        is the buffer where to write the message to.
     */
    void getLocalizedMessage(StringTranslatorIF nationalizer, StringBuffer message);

}