/* $Id$ */
package net.sf.mmm.nls.base;

import java.text.MessageFormat;
import java.util.Locale;

import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.api.NlsObjectIF;
import net.sf.mmm.nls.api.StringTranslatorIF;

/**
 * This is the implementation of the NlsMessageIF interface. It is NOT thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsMessage implements NlsMessageIF {

    /** the universal translator for {@link #getLocalizedMessage()} */
    private static StringTranslatorIF UNIVERSAL_TRANSLATOR = null;

    /** the internationlized message */
    private final String message;

    /** the parsed i18n message format */
    private final MessageFormat messageFormat;

    /** the dynamic (language independed) arguments */
    private final Object[] arguments;

    /**
     * an array for the {@link #arguments} that implement {@link NlsMessageIF}
     * or <code>null</code> if NONE of them does.
     */
    private final NlsObjectIF[] nlsArguments;

    /** the dynamic (language independed) arguments */
    private final Object[] copyArguments;

    /**
     * The constructor.
     * 
     * @param internationalizedMessage
     *        is the internationalized message.
     * @param messageArguments
     *        are the arguments filled into the message after nationalization.
     */
    public NlsMessage(String internationalizedMessage, Object... messageArguments) {

        super();
        this.message = internationalizedMessage;
        this.arguments = messageArguments;
        if (this.arguments.length == 0) {
            this.messageFormat = null;
        } else {
            this.messageFormat = new MessageFormat(internationalizedMessage, Locale.ENGLISH);
        }
        boolean recursive = false;
        for (int i = 0; i < this.arguments.length; i++) {
            if (this.arguments[i] instanceof NlsObjectIF) {
                recursive = true;
                break;
            }
        }
        if (recursive) {
            this.copyArguments = new Object[this.arguments.length];
            this.nlsArguments = new NlsObjectIF[this.arguments.length];
            for (int i = 0; i < this.arguments.length; i++) {
                if (this.arguments[i] instanceof NlsObjectIF) {
                    this.nlsArguments[i] = (NlsObjectIF) this.arguments[i];
                    this.copyArguments[i] = null;
                } else {
                    this.nlsArguments[i] = null;
                    this.copyArguments[i] = this.arguments[i];
                }
            }            
        } else {
            this.nlsArguments = null;
            this.copyArguments = null;
        }
    }

    /**
     * This method sets a universal {@link StringTranslatorIF translator} this
     * is used by {@link #getLocalizedMessage(StringTranslatorIF, StringBuffer)},
     * if <code>null</code> is given as translator. After the universal
     * translator is set, further calls of this method will have NO effect.<br>
     * The desired behaviour of a universal translator can depend from the
     * situation where it is used. E.g. a client application could use the
     * {@link Locale#getDefault() "default locale"} to choose the destination
     * language. In a multi-user server application a {@link ThreadLocal} may be
     * used to retrieve the appropriate {@link Locale locale}. After the
     * callers locale is determined, the universal translator should delegate to
     * the locale-specific translator.<br>
     * ATTENTION: No synchronization is performed setting the universal
     * translator. This assumes that an assignment is an atomic operation in the
     * JVM you are using. Additionally this method should be invoked in the
     * initialization phase of your application.
     * 
     * @param universalTranslator
     *        is the universal translator.
     */
    public static void setUniversalTranslator(StringTranslatorIF universalTranslator) {

        if (UNIVERSAL_TRANSLATOR == null) {
            UNIVERSAL_TRANSLATOR = universalTranslator;
        }
    }

    /**
     * @see net.sf.mmm.nls.api.NlsObjectIF#toNlsMessage()
     * {@inheritDoc}
     */
    public NlsMessageIF toNlsMessage() {

        return this;
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getInternationalizedMessage()
     * {@inheritDoc}
     */
    public String getInternationalizedMessage() {

        return this.message;
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getArgumentCount()
     * {@inheritDoc}
     */
    public int getArgumentCount() {

        return this.arguments.length;
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getArgument(int)
     * {@inheritDoc}
     */
    public Object getArgument(int index) {

        return this.arguments[index];
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getMessage()
     * {@inheritDoc}
     */
    public String getMessage() {

        return getLocalizedMessage(IdentityTranslator.INSTANCE);
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getLocalizedMessage()
     * {@inheritDoc}
     */
    public String getLocalizedMessage() {

        return getLocalizedMessage(null);
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getLocalizedMessage(net.sf.mmm.nls.api.StringTranslatorIF)
     * {@inheritDoc}
     */
    public String getLocalizedMessage(StringTranslatorIF nationalizer) {

        StringBuffer result = new StringBuffer();
        getLocalizedMessage(nationalizer, result);
        return result.toString();
    }

    /**
     * @see net.sf.mmm.nls.api.NlsMessageIF#getLocalizedMessage(net.sf.mmm.nls.api.StringTranslatorIF,
     *      java.lang.StringBuffer)
     * {@inheritDoc}
     */
    public void getLocalizedMessage(StringTranslatorIF nationalizer, StringBuffer messageBuffer) {

        if (nationalizer == null) {
            nationalizer = UNIVERSAL_TRANSLATOR;
            if (nationalizer == null) {
                nationalizer = IdentityTranslator.INSTANCE;
            }
        }
        String i18nMsg = getInternationalizedMessage();
        String localizedMessage = nationalizer.translate(i18nMsg);
        if (localizedMessage == null) {
            messageBuffer.append('#');
            localizedMessage = i18nMsg;
        }
        if (getArgumentCount() == 0) {
            messageBuffer.append(localizedMessage);
        } else {
            MessageFormat msg;
            if (localizedMessage == i18nMsg) {
                msg = this.messageFormat;
            } else {
                // TODO: this causes parsing every time this is invoked.
                // Should this be cached? Should the StringTranslatorAPI be
                // changed
                // to MessageFormat?
                msg = new MessageFormat(localizedMessage);
            }
            if (this.nlsArguments == null) {
                msg.format(this.arguments, messageBuffer, null);                
            } else {
                for (int i = 0; i < this.arguments.length; i++) {
                    if (this.nlsArguments[i] != null) {
                        this.copyArguments[i] = this.nlsArguments[i].toNlsMessage();
                    }
                }
                msg.format(this.copyArguments, messageBuffer, null);
            }
        }
    }

    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getMessage();
    }

}