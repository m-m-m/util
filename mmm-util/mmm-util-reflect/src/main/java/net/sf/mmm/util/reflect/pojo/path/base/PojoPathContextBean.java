/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.reflect.pojo.api.PojoFactory;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathRecognizer;

/**
 * This is an implementation of the {@link PojoPathContext} interface as simple
 * Java bean.
 * 
 * @see DefaultPojoPathContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathContextBean implements PojoPathContext {

  /** @see #getCache() */
  private Map<Object, Object> cache;

  /** @see #getProperties() */
  private Properties properties;

  /** @see #getRecognizer() */
  private PojoPathRecognizer recognizer;

  /** @see #getAdditionalFunctionManager() */
  private PojoPathFunctionManager additionalFunctionManager;

  /** @see #getPojoFactory() */
  private PojoFactory pojoFactory;

  /** @see #isCachingUnsafe() */
  private boolean cachingUnsafe;

  /**
   * The constructor. All fields are initially <code>null</code>.
   */
  public PojoPathContextBean() {

    super();
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
  public Map<Object, Object> getCache() {

    return this.cache;
  }

  /**
   * This method sets the {@link #getCache() cache}.
   * 
   * @param cache is the cache to set.
   */
  public void setCache(Map<Object, Object> cache) {

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

  /**
   * {@inheritDoc}
   */
  public boolean isCachingUnsafe() {

    return this.cachingUnsafe;
  }

  /**
   * This method sets the {@link #isCachingUnsafe() unsafe-caching} flag.
   * 
   * @param unsafeCaching is the {@link #isCachingUnsafe() unsafe-caching} flag
   *        to set.
   */
  public void setCachingUnsafe(boolean unsafeCaching) {

    this.cachingUnsafe = unsafeCaching;
  }

}
