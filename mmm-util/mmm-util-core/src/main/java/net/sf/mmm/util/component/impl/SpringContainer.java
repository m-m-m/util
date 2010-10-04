/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.api.ResourceAmbiguousException;
import net.sf.mmm.util.component.api.ResourceMissingException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
   */
  public SpringContainer() {

    this(new AnnotationConfigApplicationContext("net.sf.mmm"));
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
  @SuppressWarnings("unchecked")
  public <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass) {

    COMPONENT_API component = null;
    String[] componentIds = this.applicationContext.getBeanNamesForType(apiClass);
    if ((componentIds != null) && (componentIds.length > 0)) {
      if (componentIds.length > 1) {
        throw new ResourceAmbiguousException(apiClass.getName(), componentIds);
      }
      String componentId = componentIds[0];
      component = (COMPONENT_API) this.applicationContext.getBean(componentId);
    }
    if (component == null) {
      throw new ResourceMissingException(apiClass.getName());
    }
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass,
      String componentId) {

    COMPONENT_API component;
    try {
      component = this.applicationContext.getBean(componentId, apiClass);
      if (component == null) {
        throw new ResourceMissingException(componentId + " [" + apiClass.getName() + "]");
      }
    } catch (NoSuchBeanDefinitionException e) {
      throw new ResourceMissingException(componentId + " [" + apiClass.getName() + "]", e);
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
