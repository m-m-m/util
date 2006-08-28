/* $Id$ */
package net.sf.mmm.term.api;

import java.util.Iterator;

/**
 * This is the interface for a service that provides access to all available
 * functions.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FunctionServiceIF {

    /**
     * This method is used to retrieve a {@link FunctionIF function} by its
     * {@link FunctionIF#getOperatorSymbol() "operator symbol"}. <br>
     * E.g. <code>getFunctionBySymbol("+")</code> should return the add
     * function.
     * 
     * @param symbol
     *        is the operator symbol of the requested function.
     * @return the function for the given symbol.
     * @throws NoSuchFunctionException
     *         if NO such function exists.
     */
    FunctionIF getFunctionBySymbol(String symbol) throws NoSuchFunctionException;

    /**
     * This method is used to retrieve a {@link FunctionIF function} by its
     * {@link FunctionIF#getName() name}. <br>
     * E.g. <code>getFunctionByName("add")</code> should return the add
     * function.
     * 
     * @param name
     *        is the name of the requested function.
     * @return the function for the given name or <code>null</code> if no such
     *         function exists.
     * @throws NoSuchFunctionException
     *         if NO such function exists.
     */
    FunctionIF getFunctionByName(String name) throws NoSuchFunctionException;

    /**
     * This method gets the all functions that are registered by this service.
     * 
     * @return an iterator of all registered functions.
     */
    Iterator<FunctionIF> getFunctions();

}
