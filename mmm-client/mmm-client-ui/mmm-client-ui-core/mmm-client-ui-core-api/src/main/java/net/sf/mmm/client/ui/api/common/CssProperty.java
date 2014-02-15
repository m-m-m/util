/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This is the interface for a CSS style property.
 * 
 * @see LengthProperty
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CssProperty {

  /**
   * @return the name of this property when used in CSS.
   */
  String getStyleName();

  /**
   * @return the name of this property when used in JavaScript to define the member variable of an element
   *         style object.
   */
  String getMemberName();

}
