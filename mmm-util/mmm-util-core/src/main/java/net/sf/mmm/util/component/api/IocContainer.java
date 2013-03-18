/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;

/**
 * This is the interface for the container of an {@link Ioc} (inversion of control) framework. It is just use
 * as abstraction layer for frameworks like spring, guice or seam - you will not find a native implementation
 * within this project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface IocContainer extends ComponentContainer, AttributeWriteDisposed {

  /**
   * This method disposes the container (performs a shutdown and destroys all components).
   */
  @Override
  void dispose();

}
