/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.reflect.pojo.factory.api.PojoFactory;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathRecognizer;

/**
 * This is an implementation of the {@link PojoPathContext} interface as simple
 * Java bean. The {@link #getRecognizer() recognizer} is initialized with
 * {@link NoPojoPathRecognizer#INSTANCE}. All other fields are initially
 * <code>null</code>.
 * 
 * @see DefaultPojoPathContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathContextBean implements PojoPathContext {

  /** @see #getCache() */
  private Map<String, Object> cache;

  /** @see #getProperties() */
  private Properties properties;

  /** @see #getRecognizer() */
  private PojoPathRecognizer recognizer;

  /** @see #getAdditionalFunctionManager() */
  private PojoPathFunctionManager additionalFunctionManager;

  /** @see #get */
  private PojoFactory pojoFactory;

  /**
   * The constructor.
   */
  public PojoPathContextBean() {

    super();
    this.recognizer = NoPojoPathRecognizer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPathFunctionManager getAdditionalFunctionManager() {

    return this.additionalFunctionManager;
  }

  /**
   * This method sets the
   * {@link #getAdditionalFunctionManager() additional function-manager}.
   * 
   * @param additionalFunctionManager is the {@link PojoPathFunctionManager} to
   *        set.
   */
  public void setAdditionalFunctionManager(PojoPathFunctionManager additionalFunctionManager) {

    this.additionalFunctionManager = additionalFunctionManager;
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Object> getCache() {

    return this.cache;
  }

  /**
   * This method sets the {@link #getCache() cache}.
   * 
   * @param cache is the cache to set.
   */
  public void setCache(Map<String, Object> cache) {

    this.cache = cache;
  }

  /**
   * {@inheritDoc}
   */
  public Properties getProperties() {

    return this.properties;
  }

  /**
   * This method sets the {@link #getProperties() properties}.
   * 
   * @param properties are the {@link Properties} to set.
   */
  public void setProperties(Properties properties) {

    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPathRecognizer getRecognizer() {

    return this.recognizer;
  }

  /**
   * This method sets the {@link #getRecognizer() recognizer}.
   * 
   * @param recognizer is the {@link PojoPathRecognizer} to set.
   */
  public void setRecognizer(PojoPathRecognizer recognizer) {

    this.recognizer = recognizer;
  }

  /**
   * {@inheritDoc}
   */
  public PojoFactory getPojoFactory() {

    return this.pojoFactory;
  }

  /**
   * This method sets the {@link #getPojoFactory() pojo-factory}.
   * 
   * @param pojoFactory is the {@link PojoFactory} to set.
   */
  public void setPojoFactory(PojoFactory pojoFactory) {

    this.pojoFactory = pojoFactory;
  }

}
