/* $Id$ */
package net.sf.mmm.nls.base;

import java.io.PrintStream;
import java.io.PrintWriter;

import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.api.NlsThrowableIF;
import net.sf.mmm.nls.api.StringTranslatorIF;

/**
 * This is an abstract base implementation of an exception with real <em>native
 * language support</em>
 * (NLS).
 * 
 * @see NlsThrowableIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class NlsException extends Exception implements NlsThrowableIF {

    /** the internationalized message */
    private NlsMessageIF nlsMessage;

    /**
     * The constructor.<br>
     * 
     * @param internaitionalizedMessage
     *        is a short description of the problem. It is used for
     *        internationalization and should be in english language.
     * @param arguments
     *        are the arguments filled into the
     *        <code>internaitionalizedMessage</code> after nationalization.
     */
    public NlsException(String internaitionalizedMessage, Object... arguments) {

        this(new NlsMessage(internaitionalizedMessage, arguments));
    }

    /**
     * The constructor.<br>
     * 
     * @param nested
     *        is the {@link #getCause() cause} of this exception.
     * @param internaitionalizedMessage
     *        is a short description of the problem. It is used for
     *        internationalization and should be in english language.
     * @param arguments
     *        are the arguments filled into the
     *        <code>internaitionalizedMessage</code> after nationalization.
     */
    public NlsException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

        this(nested, new NlsMessage(internaitionalizedMessage, arguments));
    }

    /**
     * The constructor.
     * 
     * @param internationalizedMessage
     *        is the internationalized message describing the problem briefly.
     */
    public NlsException(NlsMessageIF internationalizedMessage) {

        super();
        this.nlsMessage = internationalizedMessage;
    }

    /**
     * The constructor.
     * 
     * @param nested
     *        is the {@link #getCause() cause} of this exception.
     * @param internationalizedMessage
     *        is the internationalized message describing the problem briefly.
     */
    public NlsException(Throwable nested, NlsMessageIF internationalizedMessage) {

        super(nested);
        this.nlsMessage = internationalizedMessage;
    }

    /**
     * This method gets the internationalized message describing the problem.
     * 
     * @return the internationalized message.
     */
    public final NlsMessageIF getNlsMessage() {

        return this.nlsMessage;
    }

    /**
     * @see net.sf.mmm.nls.api.NlsThrowableIF#printStackTrace(java.io.PrintStream,
     *      StringTranslatorIF)
     * 
     */
    public void printStackTrace(PrintStream stream, StringTranslatorIF nationalizer) {

        synchronized (stream) {
            stream.println(getLocalizedMessage(nationalizer));
            StackTraceElement[] trace = getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                stream.println("\tat " + trace[i]);
            }

            Throwable nested = getCause();
            if (nested != null) {
                stream.println("Caused by: ");
                if (nested instanceof NlsThrowableIF) {
                    ((NlsThrowableIF) nested).printStackTrace(stream, nationalizer);
                } else {
                    nested.printStackTrace(stream);
                }
            }
        }
    }

    /**
     * @see net.sf.mmm.nls.api.NlsThrowableIF#printStackTrace(java.io.PrintWriter, net.sf.mmm.nls.api.StringTranslatorIF)
     * 
     */
    public void printStackTrace(PrintWriter writer, StringTranslatorIF nationalizer) {
    
        synchronized (writer) {
            writer.println(getLocalizedMessage(nationalizer));
            StackTraceElement[] trace = getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                writer.println("\tat " + trace[i]);
            }

            Throwable nested = getCause();
            if (nested != null) {
                writer.println("Caused by: ");
                if (nested instanceof NlsThrowableIF) {
                    ((NlsThrowableIF) nested).printStackTrace(writer, nationalizer);
                } else {
                    nested.printStackTrace(writer);
                }
            }
        }
    }
    
    /**
     * @see java.lang.Throwable#getMessage()
     * 
     */
    public String getMessage() {

        return getLocalizedMessage(null);
    }

    /**
     * @see net.sf.mmm.nls.api.NlsThrowableIF#getLocalizedMessage(StringTranslatorIF)
     * 
     */
    public String getLocalizedMessage(StringTranslatorIF nationalizer) {

        StringBuffer message = new StringBuffer();
        getLocalizedMessage(nationalizer, message);
        return message.toString();
    }

    /**
     * @see net.sf.mmm.nls.api.NlsThrowableIF#getLocalizedMessage(StringTranslatorIF,
     *      java.lang.StringBuffer)
     * 
     */
    public void getLocalizedMessage(StringTranslatorIF nationalizer, StringBuffer message) {

        getNlsMessage().getLocalizedMessage(nationalizer, message);
        /*
         * Throwable nested = getCause(); if (nested != null) { MmmThrowableIF
         * mt = null; String msg = null; if (nested instanceof MmmThrowableIF) {
         * mt = (MmmThrowableIF) nested; } else { msg =
         * nested.getLocalizedMessage(); } if ((mt != null) || (msg != null)) {
         * message.append(" ["); if (mt != null) {
         * mt.getLocalizedMessage(nationalizer, message); } else {
         * message.append(msg); } message.append("]"); } }
         */
    }

}