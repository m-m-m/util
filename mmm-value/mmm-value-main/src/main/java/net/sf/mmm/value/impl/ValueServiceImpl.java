/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.util.value.ValueException;
import net.sf.mmm.value.base.AbstractValueService;

/**
 * This is the implementation of the {@link net.sf.mmm.value.api.ValueService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueServiceImpl extends AbstractValueService {

  /**
   * The constructor.
   */
  public ValueServiceImpl() {

    super();
  }

  /**
   * This method
   * {@link #addValueManager(net.sf.mmm.value.api.ValueManager) registers} a
   * {@link GenericValueManager} for the given <code>valueType</code> to this
   * service.
   * 
   * @param <V> is the value type to register.
   * @param valueType is the implementation of the value. It should have a
   *        String arg constructor and a compliant {@link Object#toString()}
   *        method.
   * @param name is the
   *        {@link net.sf.mmm.value.api.ValueManager#getName() logical name} of
   *        the value.
   * @throws ValueException if the registration fails (e.g. value for this name
   *         is already registered).
   */
  public <V> void addValueType(Class<V> valueType, String name) throws ValueException {

    if (valueType.isEnum()) {
      addValueManager(new GenericEnumValueManager(valueType, name));
    } else {
      addValueManager(new GenericValueManager<V>(valueType, name));      
    }
  }

  /**
   * This method
   * {@link #addValueManager(net.sf.mmm.value.api.ValueManager) registers} a
   * {@link GenericValueManager} for the given <code>valueType</code> to this
   * service.
   * 
   * @param <V> is the value type to register.
   * @param valueClass is the implementation of the value. It should have a
   *        String arg constructor and a compliant {@link Object#toString()}
   *        method.
   * @throws ValueException if the registration fails (e.g. value for this name
   *         is already registered).
   */
  public <V> void addValueType(Class<V> valueClass) throws ValueException {

    addValueManager(new GenericValueManager<V>(valueClass));
  }

}
