/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.impl;

import java.lang.reflect.Modifier;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.binding.base.AbstractConfigurationBindingInjector;

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
  protected ClassLoader getClassLoader() {

    return Thread.currentThread().getContextClassLoader();
  }

  /**
   * 
   * @param <O>
   * @param type
   * @return the implementation to choose for <code>type</code>.
   */
  protected <O> Class<? extends O> findImplementation(Class<O> type) {

    // TODO:
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

    Class<? extends O> implementation;
    if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
      implementation = findImplementation(type);
    } else {
      implementation = type;
    }
    return implementation.newInstance();
  }

}
