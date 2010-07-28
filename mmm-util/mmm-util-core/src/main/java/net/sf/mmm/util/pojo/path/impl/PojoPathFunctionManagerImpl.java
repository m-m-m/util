/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.pojo.path.base.DefaultPojoPathFunctionManager;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathFunctionManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class PojoPathFunctionManagerImpl extends DefaultPojoPathFunctionManager {

  /** @see #setFunctions(Map) */
  private Map<String, PojoPathFunction> functions;

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
    if (this.functions == null) {
      // registerFunction(function, functionName);
    } else {
      for (String key : this.functions.keySet()) {
        registerFunction(this.functions.get(key), key);
      }
    }
  }

  /**
   * 
   * @param functions
   */
  // @Inject
  public void setFunctions(List<PojoPathFunction> functions) {

    // TODO
  }

  /**
   * @param functions is the functions to set
   */
  public void setFunctions(Map<String, PojoPathFunction> functions) {

    getInitializationState().requireNotInitilized();
    this.functions = functions;
  }

}
