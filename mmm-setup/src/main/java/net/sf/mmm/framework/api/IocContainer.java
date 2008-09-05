/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This is the interface for the container of an inversion of control (IoC)
 * framework.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface IocContainer {

  /**
   * This method gets the component that realizes the given
   * <code>apiClass</code>.
   * 
   * @param <COMPONENT_API> is the generic type of the <code>apiClass</code>.
   * @param apiClass is the class reflecting the API of the requested component.
   *        This should be an interface.
   * @return the requested component.
   * @throws IocContainerException if there is no component registered that
   *         implements the given <code>apiClass</code> or if there are multiple
   *         such components.
   */
  <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass);

  /**
   * This method gets the component that realizes the given
   * <code>apiClass</code> and is registered under the given
   * <code>componentId</code>.
   * 
   * @param <COMPONENT_API> is the generic type of the <code>apiClass</code>.
   * @param apiClass is the class reflecting the API of the requested component.
   *        This should be an interface.
   * @param componentId is the unique ID under which the component is registered
   *        in this container.
   * @return the requested component.
   * @throws IocContainerException if there is no component registered under the
   *         given <code>componentId</code> or the registered component does NOT
   *         realize the given <code>apiClass</code>.
   */
  <COMPONENT_API> COMPONENT_API getComponent(Class<COMPONENT_API> apiClass, String componentId);

}
