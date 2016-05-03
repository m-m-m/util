/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is the interface for a {@link PojoPropertyAccessor property-accessor} that allows to
 * {@link #invoke(Object) access} a property without any argument.
 * 
 * @see PojoPropertyAccessorNonArgMode#GET
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPropertyAccessorNonArg extends PojoPropertyAccessor {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorNonArgMode getMode();

  /**
   * This method invokes the according property-method of {@code pojoInstance} with the given arguments. <br>
   * 
   * @param pojoInstance is the instance of the POJO where to access the property. Has to be an instance of
   *        the {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getPojoClass() type} from where this
   *        accessor was created for.
   * @return the result of the invocation. It will be {@code null} if void (e.g. initialize method). For
   *         a regular getter this will be the value of the property.
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor}
   *         caused an error during reflection.
   */
  Object invoke(Object pojoInstance) throws ReflectionException;

}
