/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import javax.annotation.Resource;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.term.base.AbstractFunctionService;

/**
 * This is the implementation of the {@link net.sf.mmm.term.api.FunctionService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class FunctionServiceImpl extends AbstractFunctionService {

  /**
   * The constructor.
   */
  public FunctionServiceImpl() {

    super();
  }

  /**
   * This method configures this service.
   * 
   * @param configuration is the configuration to inject.
   */
  @Resource
  public void configure(Configuration configuration) {

  }

}
