/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.pojo.path.api.PojoPathRecognizer;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is an implementation of the {@link PojoPathContext} interface as simple Java bean.
 * 
 * @see DefaultPojoPathContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathContextBean implements PojoPathContext {

  private  Map<Object, Object> cache;

  private  Properties properties;

  private  PojoPathRecognizer recognizer;

  private  ComposedValueConverter additionalConverter;

  private  PojoPathFunctionManager additionalFunctionManager;

  private  PojoFactory pojoFactory;

  /**
   * The constructor. All fields are initially {@code null}.
   */
  public PojoPathContextBean() {

    super();
  }

  @Override
  public PojoPathFunctionManager getAdditionalFunctionManager() {

    return this.additionalFunctionManager;
  }

  /**
   * This method sets the {@link #getAdditionalFunctionManager() additional function-manager}.
   * 
   * @param additionalFunctionManager is the {@link PojoPathFunctionManager} to set.
   */
  public void setAdditionalFunctionManager(PojoPathFunctionManager additionalFunctionManager) {

    this.additionalFunctionManager = additionalFunctionManager;
  }

  @Override
  public ComposedValueConverter getAdditionalConverter() {

    return this.additionalConverter;
  }

  /**
   * This method sets the {@link #getAdditionalConverter() additional converter} .
   * 
   * @param additionalConverter is the {@link ComposedValueConverter} to set.
   */
  public void setAdditionalConverter(ComposedValueConverter additionalConverter) {

    this.additionalConverter = additionalConverter;
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
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
