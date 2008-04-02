/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

import net.sf.mmm.util.reflect.ReflectionException;

/**
 * This is the interface for a {@link PojoPropertyAccessor property-accessor}
 * that allows to {@link #invoke(Object, Object) write} (modify) a property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorOneArg extends PojoPropertyAccessor {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorOneArgMode getMode();

  /**
   * This method invokes the according property-method of
   * <code>pojoInstance</code> with the given arguments.<br>
   * 
   * @param pojoInstance is the instance of the POJO where to access the
   *        property. Has to be an instance of the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getPojoType() type}
   *        from where this accessor was created for.
   * @param argument is the value of the property to set.
   * @return the result of the invocation. Will be <code>null</code> if void
   *         (e.g. regular setter method).
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object invoke(Object pojoInstance, Object argument) throws ReflectionException;

}
