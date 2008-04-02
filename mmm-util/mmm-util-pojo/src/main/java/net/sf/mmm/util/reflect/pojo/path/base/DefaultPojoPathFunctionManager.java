/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionManager;

/**
 * This is the default implementation of the {@link PojoPathFunctionManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class DefaultPojoPathFunctionManager implements PojoPathFunctionManager {

  /** @see #getFunction(String) */
  private final Map<String, PojoPathFunction> functionMap;

  /**
   * The constructor.
   */
  public DefaultPojoPathFunctionManager() {

    this(new HashMap<String, PojoPathFunction>());
  }

  /**
   * The constructor.
   * 
   * @param functionMap is the underlying {@link Map} with the
   *        {@link #getFunction(String) functions}.
   */
  public DefaultPojoPathFunctionManager(Map<String, PojoPathFunction> functionMap) {

    super();
    this.functionMap = functionMap;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPathFunction getFunction(String functionName) {

    return this.functionMap.get(functionName);
  }

  /**
   * This method registers the given <code>function</code> for the given
   * <code>functionName</code> so it is available via
   * {@link #getFunction(String)}.
   * 
   * @see #getFunction(String)
   * 
   * @param function is the {@link PojoPathFunction} to register.
   * @param functionName is the {@link #getFunction(String) associated name}.
   * @return the {@link PojoPathFunction} that was registered before for the
   *         given <code>functionName</code> and has now been replaced.
   */
  public PojoPathFunction registerFunction(PojoPathFunction function, String functionName) {

    return this.functionMap.put(functionName, function);
  }

}
