/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.value.NlsBundleValueMain;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the exception thrown if a value is requested by its
 * {@link net.sf.mmm.value.api.ValueManager#getName() name} but is NOT
 * {@link net.sf.mmm.value.api.ValueService#getManager(String) registered}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotRegisteredException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 1584341208707126020L;

  /**
   * The constructor.
   * 
   * @param valueName
   *        is the {@link net.sf.mmm.value.api.ValueManager#getName() name}
   *        of the value that was requested but is NOT registered.
   */
  public ValueNotRegisteredException(String valueName) {

    super(NlsBundleValueMain.ERR_NOT_REGISTERED, valueName);
  }

  /**
   * The constructor.
   * 
   * @param valueType
   *        is the
   *        {@link net.sf.mmm.value.api.ValueManager#getValueType() type} of
   *        the value that was requested but is NOT registered.
   */
  public ValueNotRegisteredException(Class valueType) {

    super(NlsBundleValueMain.ERR_NOT_REGISTERED, valueType.getName());
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.value.api.ValueManager#getName() name} or
   * {@link net.sf.mmm.value.api.ValueManager#getValueType() type} of the
   * value that was requested but NOT registered.
   * 
   * @return the value name or {@link Class#getName() type}.
   */
  public String getValueNameOrType() {

    return (String) getNlsMessage().getArgument(0);
  }

}
