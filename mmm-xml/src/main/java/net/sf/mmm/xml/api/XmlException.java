/* $Id$ */
package net.sf.mmm.xml.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is thrown if something went wrong with XML serialization or
 * parsing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlException extends NlsException {

    /** uid for serialization */
    private static final long serialVersionUID = 3257850978257613621L;

    /**
     * @see NlsException#NlsException(String, Object[])
     * {@inheritDoc}
     */
    public XmlException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsException#NlsException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public XmlException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}