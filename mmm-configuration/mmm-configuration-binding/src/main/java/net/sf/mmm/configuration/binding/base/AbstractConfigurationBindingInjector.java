/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.base;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingException;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingService;
import net.sf.mmm.util.collection.CollectionUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.base.AbstractGenericValue;

/**
 * This is the abstract base implementation of the
 * {@link ConfigurationBindingInjector} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationBindingInjector implements ConfigurationBindingInjector {

  /**
   * The constructor.
   */
  public AbstractConfigurationBindingInjector() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector#inject(net.sf.mmm.configuration.api.Configuration,
   *      net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessor, Object,
   *      net.sf.mmm.configuration.binding.api.ConfigurationBindingService)
   */
  @SuppressWarnings("unchecked")
  public void inject(Configuration configuration, PojoPropertyAccessor accessor, Object pojo,
      ConfigurationBindingService bindingService) throws ConfigurationException {

    try {
      Class<?> type = accessor.getPropertyClass();
      if (accessor.getAccessMode() == PojoPropertyAccessMode.ADD) {
        // handle add-accessor...
        boolean simpleType = isSimpleType(type);
        for (Configuration child : configuration.getDescendants("*")) {
          Object value;
          if (simpleType) {
            value = child.getValue().getValue(type);
          } else {
            value = create(child, type);
            bindingService.configure(configuration, value, this);
          }
          accessor.set(pojo, value);
        }
      } else if ((configuration.getType() == Configuration.Type.ATTRIBUTE) || isSimpleType(type)) {
        // handle plain simple type
        Object value = configuration.getValue().getValue(accessor.getPropertyClass());
        accessor.set(pojo, value);
      } else {
        Type componentType = accessor.getPropertyComponentType();
        if (componentType != null) {
          // handle list-like property...
          Class<?> componentClass = ReflectionUtil.toClass(componentType);
          Collection<? extends Configuration> children = configuration.getDescendants("*");
          int childCount = children.size();
          Object[] elements;
          if (type.isArray()) {
            elements = (Object[]) Array.newInstance(componentClass, childCount);
          } else {
            elements = new Object[childCount];
          }
          boolean simpleType = isSimpleType(componentClass);
          int childIndex = 0;
          for (Configuration child : children) {
            Object value;
            if (simpleType) {
              value = child.getValue().getValue(componentClass);
            } else {
              value = create(child, componentClass);
              bindingService.configure(configuration, value, this);
            }
            elements[childIndex++] = value;
          }
          if (type.isArray()) {
            accessor.set(pojo, elements);
          } else {
            Collection collection = CollectionUtil.create((Class<? extends Collection>) type);
            Collections.addAll(collection, elements);
            accessor.set(pojo, collection);
          }
        } else {
          // handle plain complex property...
          Object value = create(configuration, type);
          // TODO: what if the attributes have been used as constructor
          // arguments?
          bindingService.configure(configuration, value, this);
          accessor.set(pojo, value);
        }
      }
    } catch (ConfigurationBindingException e) {
      throw e;
    } catch (Exception e) {
      throw new ConfigurationBindingException(e, "ERROR");
    }
  }

  /**
   * This method determines if the given <code>type</code> is a simple type.
   * This means that it is directly supported by
   * {@link GenericValue#getValue(Class)}.
   * 
   * @param type is the type to check.
   * @return <code>true</code> if the given type is simple, <code>false</code>
   *         otherwise.
   */
  protected boolean isSimpleType(Class<?> type) {

    return AbstractGenericValue.isSupported(type);
  }

  /**
   * This method creates an instance of the given <code>type</code>.
   * 
   * @param <O> is the templated type of the object to create.
   * @param configuration is the configuration that may help to find the
   *        appropriate implementation or to get constructor arguments from.
   * @param type is the class reflecting the type of the requested object.
   * @return a new instance of the requested object.
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying getter method.
   * @throws InstantiationException if the instantiation failed.
   */
  protected abstract <O> O create(Configuration configuration, Class<O> type)
      throws InstantiationException, IllegalAccessException;
}
