/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValidationFailure() validation failure}
 * attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteValidationFailure extends AttributeReadValidationFailure {

  /**
   * This method sets the {@link #getValidationFailure() validation failure}.
   * 
   * @param validationFailure is the validation failure text or <code>null</code> to clear the error.
   */
  void setValidationFailure(String validationFailure);

}
