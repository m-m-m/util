/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link PojoPathFunctionUndefinedException} is thrown by the {@link PojoPathNavigator} if the
 * {@link PojoPath#getSegment() segment} of a {@link PojoPath} points to a {@link PojoPathFunction} that is
 * NOT {@link PojoPathFunctionManager#getFunction(String) registered}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathFunctionUndefinedException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8352330320773584401L;

  /**
   * The constructor.
   * 
   * @param functionName is the {@link PojoPathFunctionManager#getFunction(String) name} of the undefined
   *        {@link PojoPathFunction function}.
   */
  public PojoPathFunctionUndefinedException(String functionName) {

    super(functionName, null);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param functionName is the {@link PojoPathFunctionManager#getFunction(String) name} of the undefined
   *        {@link PojoPathFunction function}.
   */
  public PojoPathFunctionUndefinedException(String functionName, Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorPojoFunctionUndefined(functionName));
  }
}
