/* $Id$ */
package net.sf.mmm.nls.api;

/**
 * This is the simplest callback interface possible for translating a string to
 * a {@link java.util.Locale locale}-specific language.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface StringTranslator {

    /**
     * This method translates a given message string.<br>
     * Instead of using keys for direct lookup of the message from a
     * {@link java.util.ResourceBundle} this method takes the message in english
     * language. An implemenation may use the english message for reverse
     * mapping to a message-key and then perform a translation by forward
     * mapping that key to the nationalized message. This only works if the
     * original english message was supplied from the constant defined by the
     * message key.<br>
     * A legal implementation does NOT throw
     * {@link java.lang.Throwable anything}. On multiple calls it should always
     * return the {@link String#equals(java.lang.Object) same} result for the
     * same argument.
     * 
     * @see net.sf.mmm.nls.base.AbstractResourceBundle
     * @see NlsMessage#getInternationalizedMessage()
     * 
     * @param message
     *        is the english message to translate.
     * @return the translation of the given message or <code>null</code> if no
     *         translation is available.
     */
    String translate(String message);
    //MessageFormat translate(String message);

}