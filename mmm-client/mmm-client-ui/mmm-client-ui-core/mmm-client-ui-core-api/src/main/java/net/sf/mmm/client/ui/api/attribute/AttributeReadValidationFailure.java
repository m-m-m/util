/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getValidationFailure() validation failure} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadValidationFailure {

  /**
   * This method gets the <em>validation failure</em> of this object. This is a text that explains the user
   * why the value he has entered is invalid (typically provided as tooltip of an error icon).
   * 
   * @return the validation error text or <code>null</code> for no error.
   */
  String getValidationFailure();

}
