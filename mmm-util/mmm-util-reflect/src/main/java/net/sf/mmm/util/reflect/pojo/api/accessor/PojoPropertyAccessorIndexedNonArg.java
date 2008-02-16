/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import java.lang.reflect.InvocationTargetException;

/**
 * This is the interface for a {@link PojoPropertyAccessor property-accessor}
 * that allows to {@link #invoke(Object, int) perform something} (e.g. get or
 * remove) for a given <code>index</code> of an indexed property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorIndexedNonArg extends PojoPropertyAccessor {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorIndexedNonArgMode getMode();

  /**
   * This method invokes the according property-method of
   * <code>pojoInstance</code> with the given arguments.<br>
   * 
   * @param pojoInstance is the instance of the POJO where to access the
   *        property. Has to be an instance of the
   *        {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor#getPojoType() type}
   *        from where this accessor was created for.
   * @param index is the position in the indexed property (e.g. where to get or
   *        remove an item).
   * @return the result of the invocation. Will be <code>null</code> if void
   *         (e.g. remove method).
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying getter method.
   * @throws InvocationTargetException if the POJO itself (the invoked method)
   *         throws an exception.
   */
  Object invoke(Object pojoInstance, int index) throws IllegalAccessException,
      InvocationTargetException;

}
