/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.api.accessor;

import java.lang.reflect.InvocationTargetException;

/**
 * This is the interface for a {@link PojoPropertyAccessor property-accessor}
 * that allows to {@link #invoke(Object, int, Object) apply} an item at a given
 * <code>index</code> in an indexed property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorIndexedOneArg extends PojoPropertyAccessor {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorIndexedOneArgMode getMode();

  /**
   * This method invokes the according property-method of
   * <code>pojoInstance</code> with the given arguments.<br>
   * 
   * @param pojoInstance is the instance of the POJO where to access the
   *        property. Has to be an instance of the
   *        {@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptor#getPojoType() type}
   *        from where this accessor was created for.
   * @param index is the position in the indexed property where to apply (e.g.
   *        set or insert) the given <code>item</code>.
   * @param item is the value to apply (e.g. set or insert) as element of the
   *        indexed property.
   * @return the result of the invocation. Will be <code>null</code> if void
   *         (e.g. regular setter method).
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying method.
   * @throws InvocationTargetException if the POJO itself (the invoked method)
   *         throws an exception.
   */
  Object invoke(Object pojoInstance, int index, Object item) throws IllegalAccessException,
      InvocationTargetException;

}
