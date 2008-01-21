/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import net.sf.mmm.util.value.ValueException;
import net.sf.mmm.value.NlsBundleValueCore;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.value.api.MutableGenericValue value} was edited without
 * being {@link net.sf.mmm.value.api.MutableGenericValue#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotEditableException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 8537295080260761963L;

  /**
   * The constructor.
   * 
   * @param genericValue is the node that is NOT
   *        {@link net.sf.mmm.value.api.MutableGenericValue#isEditable() editable} .
   */
  public ValueNotEditableException(MutableGenericValue genericValue) {

    super(NlsBundleValueCore.ERR_NODE_NOT_EDITABLE, genericValue);
  }

  /**
   * The constructor.
   * 
   * @param genericValue is the node that is NOT
   *        {@link net.sf.mmm.value.api.MutableGenericValue#isEditable() editable} .
   * @param nested is the throwable that caused this exception.
   */
  public ValueNotEditableException(MutableGenericValue genericValue, Throwable nested) {

    super(nested, NlsBundleValueCore.ERR_NODE_NOT_EDITABLE, genericValue);
  }

  /**
   * This method gets the node that was edited without being
   * {@link MutableGenericValue#isEditable() editable}.
   * 
   * @return the associated configuration node.
   */
  public MutableGenericValue getGenericValue() {

    return (MutableGenericValue) getNlsMessage().getArgument(0);
  }

}
