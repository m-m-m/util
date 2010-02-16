/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.IocContainer;
import net.sf.mmm.framework.api.IocContainerException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the implementation of the {@link IocContainer} using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SpringContainer implements IocContainer {

  /** The {@link ConfigurableApplicationContext} to delegate to. */
  private final ConfigurableApplicationContext applicationContext;

  /**
   * The constructor.
   * 
   * @param configPath is the path to the XML-configuration in the classpath
   *        (e.g. "net/sf/mmm/util/lang/beans-lang.xml").
   */
  public SpringContainer(String configPath) {

    this(new ClassPathXmlApplicationContext(configPath));
  }

  /**
   * The constructor.
   * 
   * @param applicationContext is the {@link ConfigurableApplicationContext} to
   *        adapt.
   */
  public SpringContainer(ConfigurableApplicationContext applicationContext) {

    super();
    this.applicationContext = applicationContext;
  }

  /**
   * {@inheritDoc}
   */
  public <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass) {

    return getComponent(apiClass, apiClass.getName());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass,
      String componentId) {

    COMPONENT_API component;
    try {
      component = (COMPONENT_API) this.applicationContext.getBean(componentId, apiClass);
      if (component == null) {
        throw new IocContainerException("Component '" + apiClass.getSimpleName()
            + "' NOT found for '" + componentId + "'!");
      }
    } catch (NoSuchBeanDefinitionException e) {
      throw new IocContainerException("Component '" + apiClass.getSimpleName()
          + "' NOT found for '" + componentId + "'!", e);
    }
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    this.applicationContext.stop();
    this.applicationContext.close();
  }
}
