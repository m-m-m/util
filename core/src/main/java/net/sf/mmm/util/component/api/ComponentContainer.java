/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This is the interface for a {@link Ioc}/context and dependency injection (CDI) container. It is just use as
 * abstraction layer for frameworks like spring, guice or seam - you will not find a native implementation
 * within this project.
 *
 * @deprecated will be removed in some future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Deprecated
public interface ComponentContainer {

  /**
   * This method gets the component that realizes the given {@code apiClass}.
   *
   * @param <COMPONENT_API> is the generic type of the {@code apiClass}.
   * @param apiClass is the class reflecting the API of the requested component. This should be an interface.
   * @return the requested component.
   * @throws ResourceMissingException if there is no component registered that implements the given
   *         {@code apiClass}.
   * @throws ResourceAmbiguousException if there are multiple components registered that implement the given
   *         {@code apiClass}.
   */
  <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass) throws ResourceAmbiguousException, ResourceMissingException;

  /**
   * This method gets the component that realizes the given {@code apiClass} and is registered under the given
   * {@code componentId}.
   *
   * @param <COMPONENT_API> is the generic type of the {@code apiClass}.
   * @param apiClass is the class reflecting the API of the requested component. This should be an interface.
   * @param componentId is the unique ID under which the component is registered in this container.
   * @return the requested component.
   * @throws ResourceMissingException if there is no component registered under the given {@code componentId}
   *         or the registered component does NOT realize the given {@code apiClass}.
   */
  <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass, String componentId) throws ResourceMissingException;

}
