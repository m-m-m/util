/* $Id: FunctionException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception represents an error that occured because of an invalid
 * implementation or usage of a {@link net.sf.mmm.term.api.FunctionIF function}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionException extends NlsException {

    /** uid for serialization */
    private static final long serialVersionUID = -1874189448219258306L;

    /**
     * @see NlsException#NlsException(String, Object[])
     * {@inheritDoc}
     */
    public FunctionException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsException#NlsException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public FunctionException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}
