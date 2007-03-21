/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainer;
import net.sf.mmm.framework.api.LifecycleManager;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.impl.LifecycleException;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the abstract base implementation of the {@link LifecycleManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLifecycleManager implements LifecycleManager {

  /** @see #getLifecyclePhases() */
  private final Set<String> phases;

  /**
   * The constructor.
   */
  public AbstractLifecycleManager() {

    super();
    this.phases = new HashSet<String>();
  }

  /**
   * This method should be called from the constructor of sub-classes to
   * register the supported lifecycle phases. This method should NOT be called
   * after this lifecycle manager is set-up.
   * 
   * @see #getLifecyclePhases()
   * 
   * @param lifecyclePhase
   *        is the lifecycle phase to register.
   */
  public final void registerLifecyclePhase(String lifecyclePhase) {

    this.phases.add(lifecyclePhase);
  }

  /**
   * {@inheritDoc}
   */
  public final Set<String> getLifecyclePhases() {

    return this.phases;
  }

  /**
   * This method performs a regular lifecycle method.
   * 
   * @param instanceContainer
   *        is the container with the component instance.
   * @param phase
   *        is the
   *        {@link ExtendedComponentDescriptor#getLifecycleMethod(String)}
   *        to perform.
   * @throws LifecycleException
   *         if the phase failed.
   */
  protected void performLifecyclePhase(ExtendedComponentInstanceContainer<?, ?> instanceContainer,
      String phase) throws LifecycleException {

    try {
      ExtendedComponentDescriptor<?, ?> descriptor = instanceContainer.getDescriptor();
      LifecycleMethod method = descriptor.getLifecycleMethod(phase);
      if (method != null) {
        method.getLifecycleMethod().invoke(instanceContainer.getPrivateInstance(),
            ReflectionUtil.NO_ARGUMENTS);
      }
      instanceContainer.setLifecycleState(phase);
    } catch (Exception e) {
      throw new LifecycleException(instanceContainer, phase, e);
    }
  }

}
