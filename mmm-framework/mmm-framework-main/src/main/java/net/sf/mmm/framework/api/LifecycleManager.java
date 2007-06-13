/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

import java.util.Set;

import net.sf.mmm.framework.base.AbstractDependency;
import net.sf.mmm.framework.impl.LifecycleException;

/**
 * This is the interface for a manager of the
 * {@link #getLifecyclePhases() lifecycle}. After a
 * {@link net.sf.mmm.framework.api.ComponentProvider component} is created and
 * {@link AbstractDependency dependencies} are injected the component
 * participates in the lifecycle of this
 * {@link LifecycleManager lifecycle-manager}.<br>
 * The {@link #getLifecyclePhases() lifecycle phases} will be performed in a
 * well-defined order. If a component does NOT
 * {@link ExtendedComponentDescriptor#getLifecycleMethods() support} a specific
 * {@link #getLifecyclePhases() phase} it will be omitted.<br>
 * TODO As far as supported, the component may be
 * {@link LifecycleMethod#LIFECYCLE_PAUSE paused}. A paused component can be
 * {@link LifecycleMethod#LIFECYCLE_RECONFIGURE reconfigured} and/or
 * {@link LifecycleMethod#LIFECYCLE_RESUME resumed}. This intermediate
 * lifecycle can repeat as often as desired.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface LifecycleManager {

  /**
   * This method gets all
   * {@link LifecycleMethod#getLifecyclePhase() lifecycle-phases} supported by
   * this manager.<br>
   * The {@link IocContainer IoC container} will verify that all lifecycle
   * phases {@link ExtendedComponentDescriptor#getLifecycleMethods() required}
   * by a
   * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component-instance}
   * are supported by the
   * {@link MutableIocContainer#addComponentProvider(ComponentProvider) used}
   * lifecycle-manager.
   * 
   * @see ExtendedComponentDescriptor#getLifecycleMethods()
   * 
   * @return an unmodifyable set containing all supported lifecycle-phases.
   */
  Set<String> getLifecyclePhases();

  /**
   * This method performs all
   * {@link ExtendedComponentDescriptor#getLifecycleMethod(String) lifecycle-methods}
   * for setting up a
   * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component-instance}.<br>
   * The following phases define the suggested setup lifecycle in the given
   * order:
   * <ol>
   * <li>{@link LifecycleMethod#LIFECYCLE_INITIALIZE initialize}</li>
   * <li>{@link LifecycleMethod#LIFECYCLE_START start}</li>
   * <li>{@link LifecycleMethod#LIFECYCLE_EXECUTE execute}</li>
   * </ol>
   * After setting up, the component is ready to use.<br>
   * If one of the lifecylce methods fails (throws an exception), the setup
   * process is stoppend and an exception is thrown.
   * 
   * @param instanceContainer contains the
   *        {@link ExtendedComponentInstanceContainer#getPrivateInstance() component-instance}
   *        to set up.
   * @throws LifecycleException if a lifecycle method failed and the component
   *         could NOT be set up.
   */
  void setupComponent(ExtendedComponentInstanceContainer<?, ?> instanceContainer)
      throws LifecycleException;

  /**
   * This method performs all
   * {@link ExtendedComponentDescriptor#getLifecycleMethod(String) lifecycle-methods}
   * for shutting down a
   * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component-instance}
   * that is NOT needed anymore.<br>
   * The following phases define the suggested shutdown lifecycle in the given
   * order:
   * <ol>
   * <li>{@link LifecycleMethod#LIFECYCLE_STOP stop}</li>
   * <li>{@link LifecycleMethod#LIFECYCLE_DISPOSE dispose}</li>
   * </ol>
   * All lifecycle methods will be called even if one or multiple of them
   * failed. If any of the methods fail, this method will end by throwing an
   * exception.
   * 
   * @param instanceContainer contains the
   *        {@link ExtendedComponentInstanceContainer#getPrivateInstance() component-instance}
   *        to shut down.
   * @throws LifecycleException if one or multiple lifecycle phase(s) failed
   *         when shutting down the component.
   */
  void shutdownComponent(ExtendedComponentInstanceContainer<?, ?> instanceContainer)
      throws LifecycleException;

}
