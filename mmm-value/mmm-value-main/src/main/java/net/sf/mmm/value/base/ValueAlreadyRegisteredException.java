/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.value.NlsBundleValueMain;

/**
 * This is the exception thrown if a value should be
 * {@link net.sf.mmm.value.api.ValueService#getManager(String) registered} with
 * a {@link net.sf.mmm.value.api.ValueManager#getName() name} or
 * {@link net.sf.mmm.value.api.ValueManager#getValueClass() type} of that is
 * already in use.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueAlreadyRegisteredException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = -2296920732825329123L;

  /**
   * The constructor.
   * 
   * @param valueName is the
   *        {@link net.sf.mmm.value.api.ValueManager#getName() name} of the
   *        value that is already
   *        {@link net.sf.mmm.value.base.AbstractValueService#addValueManager(net.sf.mmm.value.api.ValueManager) registered}.
   */
  public ValueAlreadyRegisteredException(String valueName) {

    super(NlsBundleValueMain.ERR_ALREADY_REGISTERED_NAME, valueName);
  }

  /**
   * The constructor.
   * 
   * @param valueType is the
   *        {@link net.sf.mmm.value.api.ValueManager#getValueClass() type} of
   *        the value that is already
   *        {@link net.sf.mmm.value.base.AbstractValueService#addValueManager(net.sf.mmm.value.api.ValueManager) registered}.
   */
  public ValueAlreadyRegisteredException(Class valueType) {

    super(NlsBundleValueMain.ERR_ALREADY_REGISTERED_CLASS, valueType.getName());
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.value.api.ValueManager#getName() name} or
   * {@link net.sf.mmm.value.api.ValueManager#getValueClass() type} of the value
   * that was requested but NOT registered.
   * 
   * @return the value name or {@link Class#getName() type}.
   */
  public String getValueNameOrType() {

    return (String) getNlsMessage().getArgument(0);
  }

}
