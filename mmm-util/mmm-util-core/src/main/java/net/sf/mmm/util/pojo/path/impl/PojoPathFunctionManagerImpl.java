/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.pojo.path.api.PojoPathNamedFunction;
import net.sf.mmm.util.pojo.path.base.DefaultPojoPathFunctionManager;

/**
 * This is the implementation of the {@link net.sf.mmm.util.pojo.path.api.PojoPathFunctionManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class PojoPathFunctionManagerImpl extends DefaultPojoPathFunctionManager {

  /** @see #setFunctionMap(Map) */
  private Map<String, PojoPathFunction> functionMap;

  /** @see #setFunctions(List) */
  private List<PojoPathNamedFunction> functions;

  /**
   * The constructor.
   */
  public PojoPathFunctionManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.functionMap == null) {
      // registerFunction(function, functionName);
    } else {
      for (String key : this.functionMap.keySet()) {
        registerFunction(this.functionMap.get(key), key);
      }
    }
    if (this.functions != null) {
      for (PojoPathNamedFunction function : this.functions) {
        registerFunction(function, function.getName());
      }
    }
  }

  /**
   * This method sets a {@link List} of {@link PojoPathNamedFunction named functions} to be
   * {@link #registerFunction(PojoPathFunction, String) registered} in this manager.
   * 
   * @param functions are the {@link PojoPathNamedFunction named functions} to register.
   */
  @Inject
  public void setFunctions(List<PojoPathNamedFunction> functions) {

    getInitializationState().requireNotInitilized();
    this.functions = functions;
  }

  /**
   * @param functionMap is the functions to set
   */
  public void setFunctionMap(Map<String, PojoPathFunction> functionMap) {

    getInitializationState().requireNotInitilized();
    this.functionMap = functionMap;
  }

}
