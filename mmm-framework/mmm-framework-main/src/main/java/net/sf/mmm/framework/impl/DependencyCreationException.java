/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.DependencyException;
import net.sf.mmm.framework.api.Dependency;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}
 * could NOT be resolved.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyCreationException extends DependencyException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6421581325810304694L;

  /**
   * The constructor.
   * 
   * @param sourceDescriptor is the {@link ComponentDescriptor descriptor} of
   *        the component that requested the
   *        {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}.
   * @param sourceInstanceId is the
   *        {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class, String) instance-ID}
   *        of the component causing this request.
   * @param dependency is the {@link Dependency descriptor} of the dependency
   *        that could NOT be created.
   * @param cause is the exception that caused this error.
   */
  public DependencyCreationException(ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, Dependency dependency, Exception cause) {

    super(cause, NlsBundleFrameworkCore.ERR_DEPENDENCY_CREATION, sourceDescriptor,
        sourceInstanceId, dependency);
  }

}
