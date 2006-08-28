/* $Id: ValueParseException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.value.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is thrown if the parsing of a value fails.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseException extends ValueException {

    /** uid for serialization */
    private static final long serialVersionUID = 662961335483675913L;

    /**
     * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
     * {@inheritDoc}
     */
    public ValueParseException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public ValueParseException(Throwable nested, String internaitionalizedMessage,
            Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}