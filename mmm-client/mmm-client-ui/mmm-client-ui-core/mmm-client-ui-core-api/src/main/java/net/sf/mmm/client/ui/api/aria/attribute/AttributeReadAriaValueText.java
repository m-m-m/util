/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getValueText() valueText} attribute (property) of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaValueText extends Accessibility {

  /** The name of the <code>valuetext</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_VALUE_TEXT = "aria-valuetext";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-valuetext">valuetext</a> property of this
   * object.
   * 
   * @return the {@link AttributeReadAriaValueNow#getValueNow() current value} as string or <code>null</code>
   *         if undefined.
   */
  String getValueText();

}
