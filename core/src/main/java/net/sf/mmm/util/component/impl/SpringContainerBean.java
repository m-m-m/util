/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This is the implementation of {@link net.sf.mmm.util.component.api.IocContainer} that itself is a spring-bean that
 * can be injected. This makes code portable as it can get {@link net.sf.mmm.util.component.api.IocContainer} injected
 * while working with spring, guice/GIN or any other container.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SpringContainerBean extends AbstractSpringContainer implements ApplicationContextAware {

  /**
   * The constructor.
   */
  public SpringContainerBean() {

    super();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    setContext(applicationContext);
  }

}
