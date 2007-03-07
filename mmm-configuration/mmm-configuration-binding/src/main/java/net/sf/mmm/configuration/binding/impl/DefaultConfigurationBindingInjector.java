/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.impl;

import java.lang.reflect.Modifier;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingException;
import net.sf.mmm.configuration.binding.base.AbstractConfigurationBindingInjector;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultConfigurationBindingInjector extends AbstractConfigurationBindingInjector {

  /**
   * 
   * @return the classloader to use.
   */
  protected ClassLoader getClassLoader(Class<?> type) {

    //return Thread.currentThread().getContextClassLoader();
    return type.getClassLoader();
  }

  /**
   * 
   * @param <O>
   * @param type
   * @return the implementation to choose for <code>type</code>.
   */
  protected <O> Class<? extends O> findImplementation(Class<O> type) {

    ClassLoader loader = getClassLoader(type);
    String packageName = type.getPackage().getName();
    String className = type.getCanonicalName();
    if (packageName.contains(".api.") || packageName.endsWith(".api")) {
      packageName = packageName.replace(".api", ".impl");
    }
    if (type.isInterface()) {
    }
    return type;
  }

  /**
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @see net.sf.mmm.configuration.binding.base.AbstractConfigurationBindingInjector#create(net.sf.mmm.configuration.api.Configuration,
   *      java.lang.Class)
   */
  @Override
  protected <O> O create(Configuration configuration, Class<O> type) throws InstantiationException,
      IllegalAccessException {

    Class<?> implementation;
    if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
      GenericValue implValue = configuration.getDescendant("@implementation", ConfigurationDocument.NAMESPACE_URI_CONFIGURATION).getValue();
      if (implValue.isEmpty()) {
        implementation = implValue.getJavaClass();
      } else {
        implementation = findImplementation(type);        
      }
      if (!type.isAssignableFrom(implementation)) {
        // TODO
        throw new ConfigurationBindingException("ERROR");
      }
    } else {
      implementation = type;
    }
    // TODO: find constructor, etc.
    return (O) implementation.newInstance();
  }

}
