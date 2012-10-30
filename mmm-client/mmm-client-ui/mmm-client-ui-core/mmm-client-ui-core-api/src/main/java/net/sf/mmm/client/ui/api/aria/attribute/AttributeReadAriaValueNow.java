/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getValueNow() valueNow} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaValueNow extends Accessibility {

  /** The name of the <code>valuenow</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_VALUE_NOW = "aria-valuenow";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-valuenow">valuenow</a> property of this
   * object.
   * 
   * @return the current value or <code>null</code> if undefined.
   */
  Double getValueNow();

}
