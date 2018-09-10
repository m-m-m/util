/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;

/**
 * This is the interface for the container of a {@link Cdi CDI}/{@link Ioc IoC} framework. It is just use as
 * abstraction layer for frameworks like spring, guice or seam - you will not find a native implementation
 * within this project.
 *
 * @deprecated will be removed in some future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Deprecated
public interface IocContainer extends ComponentContainer, AttributeWriteDisposed {

  /**
   * This method disposes the container (performs a shutdown and destroys all components).
   */
  @Override
  void dispose();

}
