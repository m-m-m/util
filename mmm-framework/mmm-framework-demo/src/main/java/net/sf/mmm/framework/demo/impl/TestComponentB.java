/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.demo.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import net.sf.mmm.framework.demo.api.ComponentB;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class TestComponentB implements ComponentB {

  private Log logger;

  /**
   * The constructor.
   */
  public TestComponentB() {

    super();
  }

  /**
   * This method gets the logger.
   * 
   * @return the logger.
   */
  public Log getLogger() {

    return this.logger;
  }

  /**
   * This method sets the logger.
   * 
   * @param logger is the logger to set.
   */
  @Resource
  public void setLogger(Log logger) {

    this.logger = logger;
  }

  /**
   * 
   */
  @PostConstruct
  public void init() {

    getLogger().info("B initialized");
  }

  @PreDestroy
  public void dispose() {

    getLogger().info("B disposed");
  }

  /**
   * {@inheritDoc}
   */
  public String sayBe() {

    return "Beehh";
  }

}
