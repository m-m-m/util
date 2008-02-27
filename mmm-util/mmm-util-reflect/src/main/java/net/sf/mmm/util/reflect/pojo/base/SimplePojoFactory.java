/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

import net.sf.mmm.util.reflect.InstantiationFailedException;
import net.sf.mmm.util.reflect.pojo.api.PojoFactory;

/**
 * This is the simplest implementation of the {@link PojoFactory} interface.<br>
 * It uses {@link Class#newInstance()} to create
 * {@link #newInstance(Class) new instances}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePojoFactory implements PojoFactory {

  /**
   * {@inheritDoc}
   */
  public <POJO> POJO newInstance(Class<POJO> pojoType) throws InstantiationFailedException {

    try {
      return pojoType.newInstance();
    } catch (Exception e) {
      throw new InstantiationFailedException(e, pojoType);
    }
  }
}
