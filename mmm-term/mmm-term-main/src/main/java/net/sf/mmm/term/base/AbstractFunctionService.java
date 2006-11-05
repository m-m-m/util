/* $Id$ */
package net.sf.mmm.term.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.term.MainNlsResourceBundle;
import net.sf.mmm.term.api.FunctionException;
import net.sf.mmm.term.api.Function;
import net.sf.mmm.term.api.FunctionService;
import net.sf.mmm.term.api.NoSuchFunctionException;
import net.sf.mmm.util.collection.ReadOnlyIterator;

/**
 * This is the abstract base implementation of the FunctionService interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractFunctionService implements FunctionService {

  /**
   * mapps the {@link Function#getName() "function name"} to the
   * {@link Function function} itself.
   */
  private Map<String, Function> functionsByName;

  /**
   * mapps the {@link Function#getOperatorSymbol() "function operator symbol"}
   * to the {@link Function function} itself.
   */
  private Map<String, Function> functionsBySymbol;

  /**
   * The constructor.
   */
  public AbstractFunctionService() {

    super();
    this.functionsByName = new HashMap<String, Function>();
    this.functionsBySymbol = new HashMap<String, Function>();
  }

  /**
   * This method registers a function in this service.
   * 
   * @param function
   *        is the function to register.
   * @throws FunctionException
   *         if the name or operator symbol (as defined) is already in use by a
   *         registered function.
   */
  protected void registerFunction(Function function) throws FunctionException {

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
   * @see net.sf.mmm.term.api.FunctionService#getFunctionBySymbol(java.lang.String)
   */
  public Function getFunctionBySymbol(String symbol) throws NoSuchFunctionException {

    Function f = this.functionsBySymbol.get(symbol);
    if (f == null) {
      throw new NoSuchFunctionException(symbol);
    }
    return f;
  }

  /**
   * @see net.sf.mmm.term.api.FunctionService#getFunctionByName(java.lang.String)
   */
  public Function getFunctionByName(String name) throws NoSuchFunctionException {

    Function f = this.functionsByName.get(name);
    if (f == null) {
      throw new NoSuchFunctionException(name);
    }
    return f;
  }

  /**
   * @see net.sf.mmm.term.api.FunctionService#getFunctions()
   */
  public Iterator<Function> getFunctions() {

    return new ReadOnlyIterator<Function>(this.functionsByName.values().iterator());
  }

}
