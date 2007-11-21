/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import java.lang.reflect.InvocationTargetException;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;

/**
 * This is the interface for a {@link PojoPropertyAccessor property-accessor}
 * that allows to {@link #invoke(Object) read} a property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorNonArg extends PojoPropertyAccessor {

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode();

  /**
   * This method gets the according property of <code>pojoInstance</code>.<br>
   * 
   * @param pojoInstance is the instance of the POJO where to get the property
   *        from. Has to be an instance of the
   *        {@link PojoDescriptor#getPojoType() type} from where this accessor
   *        was created for.
   * @return the value of the property.
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying getter method.
   * @throws InvocationTargetException if the POJO itself (the getter) throws an
   *         exception.
   */
  Object invoke(Object pojoInstance) throws IllegalAccessException, InvocationTargetException;

}
