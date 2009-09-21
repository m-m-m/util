/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.lang.reflect.Array;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the simplest implementation of the {@link PojoFactory} interface.<br>
 * It uses {@link Class#newInstance()} to create {@link #newInstance(Class) new
 * instances}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePojoFactory extends AbstractLoggable implements PojoFactory {

  /**
   * {@inheritDoc}
   */
  public <POJO> POJO newInstance(Class<POJO> pojoType) throws InstantiationFailedException {

    try {
      if (pojoType.isInterface()) {
        POJO result = newInstanceForInterface(pojoType);
        if (result == null) {
          throw new InstantiationFailedException(pojoType);
        }
        return result;
      } else if (pojoType.isArray()) {
        return pojoType.cast(Array.newInstance(pojoType.getComponentType(), 0));
      } else {
        return newInstanceForClass(pojoType);
      }
    } catch (Exception e) {
      throw new InstantiationFailedException(e, pojoType);
    }
  }

  /**
   * Implementation of {@link #newInstance(Class)} for regular class.
   * 
   * @param <POJO> is the generic type of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoType is the {@link Class} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given <code>pojoType</code>.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  protected <POJO> POJO newInstanceForClass(Class<POJO> pojoType)
      throws InstantiationFailedException {

    try {
      return pojoType.newInstance();
    } catch (Exception e) {
      throw new InstantiationFailedException(e, pojoType);
    }
  }

  /**
   * This method is invoked from {@link #newInstance(Class)} if the given
   * {@link Class} is an {@link Class#isInterface() interface}.
   * 
   * @param <POJO> is the generic type of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoInterface is the interface reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given <code>pojoType</code> or
   *         <code>null</code> if no implementation could be found.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  protected <POJO> POJO newInstanceForInterface(Class<POJO> pojoInterface)
      throws InstantiationFailedException {

    return null;
  }
}
