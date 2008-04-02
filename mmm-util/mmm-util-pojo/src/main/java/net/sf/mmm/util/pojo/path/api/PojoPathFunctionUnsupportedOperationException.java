/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.pojo.NlsBundlePojo;

/**
 * A {@link PojoPathFunctionUnsupportedOperationException} is thrown if a
 * {@link PojoPathFunction} does NOT support a specific operation that was
 * invoked.<br>
 * E.g. a {@link PojoPathFunction} may be read-only and therefore NOT support
 * the operation
 * {@link PojoPathFunction#set(Object, String, Object, PojoPathContext) set}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathFunctionUnsupportedOperationException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = 4448578461102986943L;

  /**
   * The constructor.
   * 
   * @param operation is the operation that is NOT supported (e.g. "set").
   * @param function describes the {@link PojoPathFunction} that caused this
   *        exception. This string should contain the logical
   *        {@link PojoPathFunctionManager#getFunction(String) function-name}.
   */
  public PojoPathFunctionUnsupportedOperationException(String operation, String function) {

    super(NlsBundlePojo.ERR_FUNCTION_UNSUPPORTED_OPERATION, operation, function);
  }

}
