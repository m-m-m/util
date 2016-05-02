/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.api.ResourceAmbiguousException;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.ObjectDisposedException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This is the abstract base implementation of the {@link IocContainer} using spring. <br>
 * <b>ATTENTION:</b><br>
 * Please check your dependencies as <code>spring</code> is an optional dependency in
 * <code>mmm-util-core</code>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractSpringContainer extends AbstractLoggableComponent implements IocContainer {

  /** The {@link ConfigurableApplicationContext} to delegate to. */
  private ApplicationContext applicationContext;

  /**
   * The constructor.
   *
   * @param applicationContext is the {@link ConfigurableApplicationContext} to adapt.
   */
  protected AbstractSpringContainer(ConfigurableApplicationContext applicationContext) {

    super();
    this.applicationContext = applicationContext;
    initialize();
  }

  /**
   * The constructor.
   */
  protected AbstractSpringContainer() {

    super();
  }

  /**
   * @return the Spring context.
   */
  protected ApplicationContext getContext() {

    getInitializationState().requireInitilized();
    if (this.applicationContext == null) {
      throw new ObjectDisposedException(this);
    }
    return this.applicationContext;
  }

  /**
   * @param context is the {@link ApplicationContext} to set.
   */
  void setContext(ApplicationContext context) {

    getInitializationState().requireNotInitilized();
    this.applicationContext = context;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass) {

    COMPONENT_API component = null;
    String[] componentIds = getContext().getBeanNamesForType(apiClass);
    if ((componentIds != null) && (componentIds.length > 0)) {
      if (componentIds.length > 1) {
        throw new ResourceAmbiguousException(apiClass.getName(), componentIds);
      }
      String componentId = componentIds[0];
      component = (COMPONENT_API) getContext().getBean(componentId);
    }
    if (component == null) {
      throw new ResourceMissingException(apiClass.getName());
    }
    return component;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass, String componentId) {

    COMPONENT_API component;
    try {
      component = getContext().getBean(componentId, apiClass);
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
  @Override
  public void dispose() {

    if (this.applicationContext != null) {
      if (this.applicationContext instanceof ConfigurableApplicationContext) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) this.applicationContext;
        context.stop();
        context.close();
      }
      this.applicationContext = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    return (this.applicationContext == null);
  }
}
