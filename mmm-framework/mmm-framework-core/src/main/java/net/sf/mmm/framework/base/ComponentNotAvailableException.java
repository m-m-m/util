/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentException;

/**
 * A {@link ComponentNotAvailableException} is thrown if a
 * {@link net.sf.mmm.framework.api.ComponentProvider component} was
 * {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class) requested}
 * that is NOT
 * {@link net.sf.mmm.framework.api.ComponentManager#hasComponent(Class) available}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentNotAvailableException extends ComponentException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4615431837761683276L;

  /**
   * The constructor.
   * 
   * @param specification
   *        is the
   *        {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class) requested}
   *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
   *        that is NOT
   *        {@link net.sf.mmm.framework.api.ComponentManager#hasComponent(Class) available}.
   */
  public ComponentNotAvailableException(Class specification) {

    super(NlsBundleFrameworkCore.ERR_COMPONENT_NOT_AVAILABLE, specification);
  }

}
