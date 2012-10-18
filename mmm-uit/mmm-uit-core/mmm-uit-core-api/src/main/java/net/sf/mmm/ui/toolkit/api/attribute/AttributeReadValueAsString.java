/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives advanced read access to the {@link #getValueAsString() valueAsString} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadValueAsString {

  /**
   * This method gets the {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} as
   * {@link String}. It returns the value as it is entered by the end-user that may be invalid.
   * 
   * @return the value as entered by the user. Will be the empty {@link String} if the field is blank.
   */
  String getValueAsString();

}
