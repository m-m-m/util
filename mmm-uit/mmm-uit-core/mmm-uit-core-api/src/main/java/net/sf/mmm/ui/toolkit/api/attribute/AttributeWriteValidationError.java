/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValidationError() validation error} attribute
 * of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteValidationError extends AttributeReadValidationError {

  /**
   * This method sets the {@link #getValidationError() validation error}.
   * 
   * @param validationError is the validation error text or <code>null</code> to clear the error.
   */
  void setValidationError(String validationError);

}
