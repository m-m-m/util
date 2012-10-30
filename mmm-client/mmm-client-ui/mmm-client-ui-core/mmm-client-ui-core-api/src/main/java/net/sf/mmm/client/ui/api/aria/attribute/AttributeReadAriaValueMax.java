/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getValueMax() valueMax} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaValueMax extends Accessibility {

  /** The name of the <code>valuemax</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_VALUE_MAX = "aria-valuemax";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-valuemax">valuemax</a> property of this
   * object.
   * 
   * @return the maximum value of a range. The default (if NOT set) is {@link Double#MAX_VALUE}.
   */
  double getValueMax();

}
