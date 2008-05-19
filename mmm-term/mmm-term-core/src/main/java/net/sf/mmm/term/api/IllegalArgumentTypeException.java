/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.term.NlsBundleTermCore;
import net.sf.mmm.util.reflect.api.Arguments;
import net.sf.mmm.util.reflect.api.Signature;

/**
 * This exception represents an error that occured during the calculation of a
 * function. There can be various reasons for a calculation exception e.g. zero
 * divide, or incompatible types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalArgumentTypeException extends CalculationException {

  /** uid for serialization */
  private static final long serialVersionUID = -8654301245551735738L;

  /**
   * The constructor.
   * 
   * @param function the function that declared the signature as illegal.
   * @param signature is the signature that was illegal.
   */
  public IllegalArgumentTypeException(Function function, Signature signature) {

    super(NlsBundleTermCore.ERR_ILLEGAL_SIGNATURE, function, signature);
  }

  /**
   * The constructor.
   * 
   * @param function the function that declared the argument as illegal.
   * @param arguments are the arguments that were illegal.
   */
  public IllegalArgumentTypeException(Function function, Arguments arguments) {

    super(NlsBundleTermCore.ERR_ILLEGAL_ARGUMENTS, function, arguments);
  }

  /**
   * The constructor.
   * 
   * @param function the function that declared the argument as illegal.
   * @param arguments are the arguments that were illegal.
   * @param nested is the throwable that {@link Throwable#getCause() caused}
   *        this exception.
   */
  public IllegalArgumentTypeException(Function function, Arguments arguments, Throwable nested) {

    super(nested, NlsBundleTermCore.ERR_ILLEGAL_ARGUMENTS, function, arguments);
  }

  /**
   * This method gets the function that threw this error and declared the
   * argument(s) it was given as calculation input as illegal.
   * 
   * @return the function.
   */
  public Function getFunction() {

    return ((Function) getNlsMessage().getArgument(0));
  }

}
