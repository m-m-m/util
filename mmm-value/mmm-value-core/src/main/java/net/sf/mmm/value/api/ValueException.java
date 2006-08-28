/* $Id: ValueException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.value.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is thrown if a something goes wrong about values. This can be
 * an invalid "casting", a parse error, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueException extends NlsRuntimeException {

    /** uid for serialization */
    private static final long serialVersionUID = -8445209659250789499L;

    /**
     * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
     * {@inheritDoc}
     */
    public ValueException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public ValueException(Throwable nested, String internaitionalizedMessage,
            Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}