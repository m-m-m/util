/* $Id$ */
package net.sf.mmm.term.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is thrown if an error occured while parsing a
 * {@link net.sf.mmm.term.api.TermIF term} ({@link net.sf.mmm.term.impl.Expression expression}).
 * This exception means that the expression is malformed and can not be parsed.
 * <br>
 * E.g. the expression "1*(" is illegal would produce such an exception.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ParseException extends NlsException {

    /** uid for serialization */
    private static final long serialVersionUID = 8299920898179225587L;

    /**
     * @see NlsException#NlsException(String, Object[])
     */
    public ParseException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see NlsException#NlsException(Throwable, String, Object[])
     */
    public ParseException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}
