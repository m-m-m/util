/* $Id$ */
package net.sf.mmm.term.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.term.MainNlsResourceBundle;
import net.sf.mmm.term.api.FunctionException;
import net.sf.mmm.term.api.FunctionIF;
import net.sf.mmm.term.api.FunctionServiceIF;
import net.sf.mmm.term.api.NoSuchFunctionException;
import net.sf.mmm.util.collection.ReadOnlyIterator;

/**
 * This is the abstract base implementation of the FunctionServiceIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractFunctionService implements FunctionServiceIF {

    /**
     * mapps the {@link FunctionIF#getName() "function name"} to the
     * {@link FunctionIF function} itself.
     */
    private Map<String, FunctionIF> functionsByName;

    /**
     * mapps the
     * {@link FunctionIF#getOperatorSymbol() "function operator symbol"} to the
     * {@link FunctionIF function} itself.
     */
    private Map<String, FunctionIF> functionsBySymbol;

    /**
     * The constructor.
     */
    public AbstractFunctionService() {

        super();
        this.functionsByName = new HashMap<String, FunctionIF>();
        this.functionsBySymbol = new HashMap<String, FunctionIF>();
    }

    /**
     * This method registers a function in this service.
     * 
     * @param function
     *        is the function to register.
     * @throws FunctionException
     *         if the name or operator symbol (as defined) is already in use by
     *         a registered function.
     */
    protected void registerFunction(FunctionIF function) throws FunctionException {

        if (this.functionsByName.containsKey(function.getName())) {
            throw new FunctionException(MainNlsResourceBundle.ERR_FCT_NAME_USED, function);
        }
        if (function.getOperatorSymbol() != null) {
            if (this.functionsBySymbol.containsKey(function.getOperatorSymbol())) {
                throw new FunctionException(MainNlsResourceBundle.ERR_FCT_SYMBOL_USED, function);
            }
            this.functionsBySymbol.put(function.getOperatorSymbol(), function);
        }
        this.functionsByName.put(function.getName(), function);
    }

    /**
     * @see net.sf.mmm.term.api.FunctionServiceIF#getFunctionBySymbol(java.lang.String)
     */
    public FunctionIF getFunctionBySymbol(String symbol) throws NoSuchFunctionException {

        FunctionIF f = this.functionsBySymbol.get(symbol);
        if (f == null) {
            throw new NoSuchFunctionException(symbol);
        }
        return f;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionServiceIF#getFunctionByName(java.lang.String)
     */
    public FunctionIF getFunctionByName(String name) throws NoSuchFunctionException {

        FunctionIF f = this.functionsByName.get(name);
        if (f == null) {
            throw new NoSuchFunctionException(name);
        }
        return f;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionServiceIF#getFunctions()
     */
    public Iterator<FunctionIF> getFunctions() {

        return new ReadOnlyIterator<FunctionIF>(this.functionsByName.values().iterator());
    }

}