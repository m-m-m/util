/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import java.util.Iterator;

/**
 * This is the interface for a service that provides access to all available
 * functions.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FunctionService {

  /**
   * This method is used to retrieve a {@link Function function} by its
   * {@link Function#getOperatorSymbol() "operator symbol"}. <br>
   * E.g. <code>getFunctionBySymbol("+")</code> should return the add
   * function.
   * 
   * @param symbol
   *        is the operator symbol of the requested function.
   * @return the function for the given symbol.
   * @throws NoSuchFunctionException
   *         if NO such function exists.
   */
  Function getFunctionBySymbol(String symbol) throws NoSuchFunctionException;

  /**
   * This method is used to retrieve a {@link Function function} by its
   * {@link Function#getName() name}. <br>
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
  Function getFunctionByName(String name) throws NoSuchFunctionException;

  /**
   * This method gets the all functions that are registered by this service.
   * 
   * @return an iterator of all registered functions.
   */
  Iterator<Function> getFunctions();

}
