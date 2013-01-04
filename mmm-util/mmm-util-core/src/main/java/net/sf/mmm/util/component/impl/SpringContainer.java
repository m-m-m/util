/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * This is a simple implementation of the {@link net.sf.mmm.util.component.api.IocContainer} using spring.<br/>
 * <b>ATTENTION:</b><br>
 * Please check your dependencies as <code>spring</code> is an optional dependency in
 * <code>mmm-util-core</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SpringContainer extends AbstractSpringContainer {

  /**
   * The constructor.
   */
  public SpringContainer() {

    this(new AnnotationConfigApplicationContext("net.sf.mmm"));
  }

  /**
   * The constructor.
   * 
   * @param packages are the {@link Package}s containing components to manage.
   */
  public SpringContainer(String... packages) {

    this(new AnnotationConfigApplicationContext(packages));
  }

  /**
   * The constructor.
   * 
   * @param applicationContext is the {@link ConfigurableApplicationContext} to adapt.
   */
  public SpringContainer(ConfigurableApplicationContext applicationContext) {

    super(applicationContext);
  }

}
