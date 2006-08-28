/* $Id: NlsIllegalArgumentException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.nls.base;

import net.sf.mmm.nls.api.NlsMessageIF;

/**
 * A {@link NlsIllegalArgumentException} is analog to an
 * {@link IllegalArgumentException} but with native language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsIllegalArgumentException extends NlsRuntimeException {

    /** UID for serialization */
    private static final long serialVersionUID = -1537683835966488723L;

    /**
     * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
     * {@inheritDoc}
     */
    public NlsIllegalArgumentException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public NlsIllegalArgumentException(Throwable nested, String internaitionalizedMessage,
            Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(NlsMessageIF)
     * {@inheritDoc}
     */
    public NlsIllegalArgumentException(NlsMessageIF internationalizedMessage) {

        super(internationalizedMessage);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(Throwable, NlsMessageIF)
     * {@inheritDoc}
     */
    public NlsIllegalArgumentException(Throwable nested, NlsMessageIF internationalizedMessage) {

        super(nested, internationalizedMessage);
    }

}
