/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.DependencyException;
import net.sf.mmm.framework.base.AbstractDependency;
import net.sf.mmm.framework.base.ComponentNotAvailableException;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}
 * could NOT be resolved.
 * 
 * @see ComponentNotAvailableException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyNotAvailableException extends DependencyException {

  /** UID for serialization. */
  private static final long serialVersionUID = -177207919686785274L;

  /**
   * The constructor.
   * 
   * @param sourceDescriptor is the {@link ComponentDescriptor descriptor} of
   *        the component that requested the
   *        {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}.
   * @param sourceInstanceId is the
   *        {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class, String) instance-ID}
   *        of the component causing this request.
   * @param dependencySpecification is the
   *        {@link AbstractDependency#getSpecification() specification} of the
   *        dependency that is NOT available.
   */
  public DependencyNotAvailableException(ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, Class dependencySpecification) {

    super(NlsBundleFrameworkCore.ERR_DEPENDENCY_NOT_AVAILABLE, sourceDescriptor, sourceInstanceId,
        dependencySpecification);
  }

}
