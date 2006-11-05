/* $Id$ */
package net.sf.mmm.term.api;

import net.sf.mmm.term.CoreNlsResourceBundle;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.term.api.Function function} was
 * {@link net.sf.mmm.term.api.Function#calculate(net.sf.mmm.environment.api.EnvironmentIF, net.sf.mmm.term.api.Term[]) calculated}
 * with an {@link Function#validateArgumentCount(int) illegal} number of
 * arguments.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalArgumentCountException extends CalculationException {

  /** uid for serialization */
  private static final long serialVersionUID = -1621232704340655203L;

  /**
   * The constructor.
   * 
   * @param argumentCount
   *        the argument count that was illegal.
   * @param function
   *        the function that declared the argument count as illegal.
   */
  public IllegalArgumentCountException(Function function, int argumentCount) {

    super(CoreNlsResourceBundle.ERR_ILLEGAL_ARG_COUNT, function, Integer.valueOf(argumentCount),
        Integer.valueOf(function.getMinimumArgumentCount()), Integer.valueOf(function
            .getMaximumArgumentCount()));
  }

  /**
   * This method gets the illegal argument count. This exception was thrown by
   * a function that did not accept the list of arguments because the length
   * was not in the range specified by the functions minimum and maximum
   * argument count.
   * 
   * @return the argument count the function declared as illegal.
   */
  public int getIllegalArgumentCount() {

    return ((Integer) getNlsMessage().getArgument(1)).intValue();
  }

  /**
   * This method gets the function that threw this error and declared the
   * number of arguments it was given as calculation input as illegal.
   * 
   * @return the function.
   */
  public Function getFunction() {

    return ((Function) getNlsMessage().getArgument(0));
  }

}
