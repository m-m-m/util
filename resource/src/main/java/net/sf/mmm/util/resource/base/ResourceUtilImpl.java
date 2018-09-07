/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.reflect.base.ResourceVisitor;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceUtil;

/**
 * Implementation of {@link ResourceUtil}.
 *
 * @author hohwille
 * @since 7.6.0
 */
public class ResourceUtilImpl extends AbstractComponent implements ResourceUtil {

  private static ResourceUtil instance;

  private ReflectionUtilImpl reflectionUtil;

  /**
   * The constructor.
   */
  public ResourceUtilImpl() {

    super();
  }

  /**
   * @return the {@link ReflectionUtil} instance.
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil the new value of {@link #getReflectionUtil()}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = (ReflectionUtilImpl) reflectionUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = (ReflectionUtilImpl) ReflectionUtilImpl.getInstance();
    }
  }

  @Override
  public Set<DataResource> findResources(String packageName, boolean includeSubPackages, Filter<? super String> filter) {

    return findResources(packageName, includeSubPackages, filter, this.reflectionUtil.getDefaultClassLoader(filter.getClass()));
  }

  @Override
  public Set<DataResource> findResources(String absoluteClasspath) {

    try {
      Set<DataResource> result = new HashSet<>();
      Enumeration<URL> resourceUrlEnumeration = this.reflectionUtil.getDefaultClassLoader(getClass()).getResources(absoluteClasspath);
      while (resourceUrlEnumeration.hasMoreElements()) {
        URL url = resourceUrlEnumeration.nextElement();
        result.add(new UrlResource(url));
      }
      return result;
    } catch (IOException e) {
      throw new IllegalStateException("Error reading resources.", e);
    }
  }

  @Override
  public Set<DataResource> findResources(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader) {

    Set<DataResource> result = new HashSet<>();
    ResourceVisitor visitor = new ResourceCollector(result, filter);
    this.reflectionUtil.visitResourceNames(packageName, includeSubPackages, classLoader, visitor);
    return result;
  }

  /**
   * This method gets the singleton instance of this {@link ResourceUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static ResourceUtil getInstance() {

    if (instance == null) {
      ResourceUtilImpl impl = null;
      synchronized (ResourceUtilImpl.class) {
        if (instance == null) {
          impl = new ResourceUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

}
