/* $Id$ */
package net.sf.mmm.term.api;

import net.sf.mmm.term.ServiceNlsResourceBundle;

/**
 * This is the exception thrown if a function was requested that ist NOT
 * available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NoSuchFunctionException extends FunctionException {

    /** UID for serialization */
    private static final long serialVersionUID = -1205400349394700623L;

    /**
     * The constructor.
     * 
     * @param nameOrSymbol
     *        is the name or symbol of the requested function.
     */
    public NoSuchFunctionException(String nameOrSymbol) {

        super(ServiceNlsResourceBundle.ERR_FCT_NO_SUCH_NAME_OR_SYMBOL, nameOrSymbol);
    }
}
