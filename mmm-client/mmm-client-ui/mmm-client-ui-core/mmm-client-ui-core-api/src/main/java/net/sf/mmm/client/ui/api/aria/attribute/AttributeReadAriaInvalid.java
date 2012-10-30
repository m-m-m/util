/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaInvalid;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getInvalid() invalid} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaInvalid extends Accessibility {

  /** The name of the <code>invalid</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_INVALID = "aria-invalid";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-invalid">invalid</a> state of this object.
   * 
   * @return the {@link AriaInvalid} indicating if the validation state of this object. The default (if NOT
   *         set) is {@link AriaInvalid#FALSE}.
   */
  AriaInvalid getInvalid();

}
